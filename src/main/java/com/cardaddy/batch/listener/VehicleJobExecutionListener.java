package com.cardaddy.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class VehicleJobExecutionListener  implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("beforeJob");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("afterJob {}", jobExecution.getStatus());
    }
}
