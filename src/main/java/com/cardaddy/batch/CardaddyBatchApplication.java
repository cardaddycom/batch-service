package com.cardaddy.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class CardaddyBatchApplication implements ApplicationRunner {

    final Job importVehicleJob;
    final JobLauncher jobLauncher;

    public static void main(String[] args) {
        SpringApplication.run(CardaddyBatchApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        if(!args.containsOption("importTaskId")) {
            throw new IllegalArgumentException("Must contain --importTaskId argument");
        }

        String importTaskId =  args.getOptionValues("importTaskId").get(0);
        log.info("importTaskId id {}", importTaskId);

        try {
            log.info("Start Batch");
            JobParameters jobParameters =
                    new JobParametersBuilder()
                            .addString("importTaskId",importTaskId)
                            .addDate("date", new Date()).toJobParameters();

            JobExecution execution = jobLauncher.run(importVehicleJob, jobParameters);
            log.info("Exit Status : {}", execution.getStatus());

            //Exit with success
            System.exit(0);
        } catch (Exception e) {
            //Exit with a failure
            System.exit(-1);
            e.printStackTrace();
        }

    }

}
