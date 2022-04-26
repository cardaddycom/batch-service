package com.cardaddy.batch.tasklet;

import com.cardaddy.batch.domain.task.imports.ImportTask;
import com.cardaddy.batch.repository.ImportTaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class FtpGetRemoteFilesTasklet implements Tasklet, InitializingBean {

    @Value("${cardaddy.import.job-id}")
    private Long jobId;

    @Autowired
    private ImportTaskRepository repository;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.info("DownLoadCsvFromFtpTasklet");
        ImportTask importTask = repository.getById(jobId);
        log.debug("ImportTask {}", importTask);
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
