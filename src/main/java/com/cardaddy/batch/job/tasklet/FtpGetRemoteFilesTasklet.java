package com.cardaddy.batch.job.tasklet;

import com.cardaddy.batch.domain.task.imports.ImportTask;
import com.cardaddy.batch.repository.ImportTaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class FtpGetRemoteFilesTasklet implements Tasklet, StepExecutionListener {

    @Autowired
    private ImportTaskRepository repository;

    @Value("#{jobParameters['importTaskId']}")
    private Long importTaskId;

    @Value("${ftp.root}")
    private String root;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.info("DownLoadCsvFromFtpTasklet");
        ImportTask importTask = repository.getById(importTaskId);
        log.debug("Import Task {}", importTask);
        fetchFileFromFTP(importTask);
        return RepeatStatus.FINISHED;
    }

    public void fetchFileFromFTP(ImportTask importTask) throws IOException {
        //new ftp client
        FTPClient ftp = new FTPClient();
        try {
            //try to connect
            ftp.connect(importTask.getFtpAccount().getFtpHost());

            int reply = ftp.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                throw new Exception("Connect failed: " + ftp.getReplyString());
            }

            //login to server
            if (!ftp.login(importTask.getFtpAccount().getFtpUser(), importTask.getFtpAccount().getFtpPass())) {
                throw new Exception("Login failed: " + ftp.getReplyString());
            }

            //enter passive mode
            ftp.enterLocalPassiveMode();

            if (!ftp.setFileType(FTP.BINARY_FILE_TYPE)) {
                throw new IOException("Setting binary file type failed.");
            }

            String fileName = importTask.isZipfile() ? importTask.getZipFilename() : importTask.getFilename();

            File tempFile = File.createTempFile("tempfile", null);

            try (OutputStream tempOut = new FileOutputStream(tempFile)) {
                ftp.retrieveFile(fileName, tempOut);
                tempOut.flush();
            }

            Path outputFile = Paths.get(String.format("%s/%s/home/%s", root, importTask.getFtpAccount().getId(), fileName));

            if(outputFile!=null){
                if(Files.notExists(outputFile)){
                    Files.createDirectories(outputFile);
                }
            }

            Files.deleteIfExists(outputFile);

            Files.move(tempFile.toPath(), outputFile);
            ftp.logout();

        } catch (Exception ex) {
            throw new IOException(ex);
        } finally {
            ftp.disconnect();
        }
    }

    public InputStream getZipData(ZipInputStream zip, String filename) throws FileNotFoundException, IOException {
        for (ZipEntry e; (e = zip.getNextEntry()) != null;) {
            if (e.getName().equals(filename)) {
                return zip;
            }
        }
        throw new FileNotFoundException("zip://" + filename);
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
