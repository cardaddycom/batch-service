package com.cardaddy.batch.job.tasklet;

import com.cardaddy.batch.domain.task.imports.ImportTask;
import com.cardaddy.batch.repository.ImportTaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class UnZipFileTasklet implements Tasklet {

    @Autowired
    private ImportTaskRepository repository;

    @Value("${ftp.root}")
    private String root;

    @Value("#{jobParameters['importTaskId']}")
    private Long importTaskId;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        ImportTask importTask = repository.getById(importTaskId);

        if(importTask.isZipfile()) {
            Path source = Paths.get(String.format(String.format("%s/%s/home/%s", root, importTask.getFtpAccount().getId(), importTask.getZipFilename())));
            Path target = Paths.get(String.format(String.format("%s/%s/home/", root, importTask.getFtpAccount().getId())));

            unzipFolder(source, target);
        }

        return RepeatStatus.FINISHED;
    }

    public static void unzipFolder(Path source, Path target) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(source.toFile()))) {
            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {
                Path newPath = zipSlipProtect(zipEntry, target);
                Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        }
    }

    // protect zip slip attack
    public static Path zipSlipProtect(ZipEntry zipEntry, Path targetDir) throws IOException {
        Path targetDirResolved = targetDir.resolve(zipEntry.getName());

        Path normalizePath = targetDirResolved.normalize();
        if (!normalizePath.startsWith(targetDir)) {
            throw new IOException("Bad zip entry: " + zipEntry.getName());
        }
        return normalizePath;
    }
}
