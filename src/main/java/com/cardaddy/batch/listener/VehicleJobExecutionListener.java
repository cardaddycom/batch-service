package com.cardaddy.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class VehicleJobExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("beforeJob");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(!ExitStatus.COMPLETED.equals(jobExecution.getExitStatus().getExitCode())) {
            System.exit(-1);
        }
        System.out.println("Job Completed");
    }

}
