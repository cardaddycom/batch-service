package com.cardaddy.batch.config;

import com.cardaddy.batch.job.VehicleItemReader;
import com.cardaddy.batch.job.VehicleItemWriter;
import com.cardaddy.batch.job.processors.*;
import com.cardaddy.batch.listener.VehicleItemReaderListener;
import com.cardaddy.batch.listener.VehicleItemWriterListener;
import com.cardaddy.batch.listener.VehicleJobExecutionListener;
import com.cardaddy.batch.model.FlatVehicleListing;
import com.cardaddy.batch.tasklet.DeleteVehicleTasklet;
import com.cardaddy.batch.tasklet.FtpGetRemoteFilesTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public VehicleItemReaderListener readerListener() {
        return new VehicleItemReaderListener();
    }

    @Bean
    public VehicleItemWriterListener writerListener() {
        return new VehicleItemWriterListener();
    }

    @Bean
    public VehicleJobExecutionListener jobExecutionListener() {
        return new VehicleJobExecutionListener();
    }

    @Bean
    public VehicleItemReader reader() {
        return new VehicleItemReader();
    }

    @Bean
    public VehicleItemWriter writer() {
        return new VehicleItemWriter();
    }

    @Bean
    public VehicleListingItemProcessor vehicleProcessor() {
        return new VehicleListingItemProcessor();
    }

    @Bean
    public BodyTypeItemProcessor bodTypeProcessor() {
        return new BodyTypeItemProcessor();
    }

    @Bean
    public ColorItemProcessor colorProcessor() {
        return new ColorItemProcessor();
    }

    @Bean
    public PhotoItemProcessor photoProcessor() {
        return new PhotoItemProcessor();
    }

    @Bean
    public TransmissionItemProcessor transmissionProcessor() {
        return new TransmissionItemProcessor();
    }

    @Bean
    public FtpGetRemoteFilesTasklet ftpGetRemoteFilesTasklet() {
        return new FtpGetRemoteFilesTasklet();
    }

    @Bean
    public DeleteVehicleTasklet deleteVehicleTasklet() {
        return new DeleteVehicleTasklet();
    }

    @Bean
    public Step ftpGetRemoteFilesStep() {
        return this.stepBuilderFactory.get("ftpGetRemoteFilesStep")
                .tasklet(ftpGetRemoteFilesTasklet())
                .build();
    }

    @Bean
    public Step deleteVehicleStep() {
        return this.stepBuilderFactory.get("deleteVehicleStep")
                .tasklet(deleteVehicleTasklet())
                .build();
    }

    @Bean
    public Job importVehicleJob(Step vehicleProcessingStep,
                                VehicleJobExecutionListener vehicleJobExecutionListener,
                                Step ftpGetRemoteFilesStep,
                                Step deleteVehicleStep) {
        return jobBuilderFactory.get("importVehicleJob")
                .incrementer(new RunIdIncrementer())
                .listener(vehicleJobExecutionListener)
                .start(ftpGetRemoteFilesStep)
                .next(vehicleProcessingStep)
                .next(deleteVehicleStep)
                .build();
    }

    @Bean
    public Step vehicleProcessingStep(VehicleItemReader reader,
                            BodyTypeItemProcessor bodyTypeItemProcessor,
                            ColorItemProcessor colorItemProcessor,
                            PhotoItemProcessor photoItemProcessor,
                            TransmissionItemProcessor transmissionItemProcessor,
                            VehicleListingItemProcessor vehicleListingItemProcessor) {
        return stepBuilderFactory.get("processVehiclesStep")
                .<FlatVehicleListing, FlatVehicleListing>chunk(10)
                .reader(reader)
                .processor(bodyTypeItemProcessor)
                .processor(colorItemProcessor)
                .processor(photoItemProcessor)
                .processor(transmissionItemProcessor)
                .processor(vehicleListingItemProcessor)
                .writer(writer())
                .listener(readerListener())
                .listener(writerListener())
                .build();
    }

}
