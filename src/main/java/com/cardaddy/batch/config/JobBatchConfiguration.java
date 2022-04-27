package com.cardaddy.batch.config;

import com.cardaddy.batch.domain.listing.VehicleListing;
import com.cardaddy.batch.domain.task.imports.ImportTask;
import com.cardaddy.batch.domain.task.lookup.ImportConfiguration;
import com.cardaddy.batch.job.VehicleItemWriter;
import com.cardaddy.batch.job.processors.*;
import com.cardaddy.batch.listener.VehicleItemReaderListener;
import com.cardaddy.batch.listener.VehicleItemWriterListener;
import com.cardaddy.batch.listener.VehicleJobExecutionListener;
import com.cardaddy.batch.model.FlatVehicleListing;
import com.cardaddy.batch.repository.ImportConfigurationRepository;
import com.cardaddy.batch.repository.ImportTaskRepository;
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
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
    public VehicleItemWriter writer() {
        return new VehicleItemWriter();
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
    public Step vehicleProcessingStep(FlatFileItemReader<FlatVehicleListing> reader) {
        return stepBuilderFactory.get("processVehiclesStep")
                .<FlatVehicleListing, VehicleListing>chunk(10)
                .reader(reader)
                .processor(compositeProcessor())
                .writer(writer())
                .listener(readerListener())
                .listener(writerListener())
                .build();
    }

    @Bean
    public CompositeItemProcessor compositeProcessor() {
        List<ItemProcessor> delegates = new ArrayList<>(2);
        delegates.add(new BodyTypeItemProcessor());
        delegates.add(new ColorItemProcessor());
        delegates.add(new PhotoItemProcessor());
        delegates.add(new TransmissionItemProcessor());
        delegates.add(new VehicleListingItemProcessor());

        CompositeItemProcessor processor = new CompositeItemProcessor();
        processor.setDelegates(delegates);
        return processor;
    }

    @Bean
    @Transactional(readOnly = true)
    public FlatFileItemReader<FlatVehicleListing> reader(ImportTaskRepository importTaskRepository,
                                                         ImportConfigurationRepository importConfigurationRepository) {
        log.info("Flat File Item Reader");
        ImportTask importTask = importTaskRepository.getById(1L);
        log.debug("importTask {}", importTask.getFilename());

        List<ImportConfiguration> configurationList = importConfigurationRepository.getByImportSystemOrderByCsvColumnPositionAsc(importTask.getImportSystem());

        int configSize = configurationList.size();

        int[] columnPositions = new int[configSize];
        String[] columnNames = new String[configSize];

        IntStream.range(0, configSize).forEachOrdered(i -> {
            ImportConfiguration configuration = configurationList.get(i);
            columnPositions[i] = configuration.getCsvColumnPosition();
            columnNames[i] = configuration.getImportField().getName();
        });

        FlatFileItemReader reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(importTask.getFilename()));
        reader.setLinesToSkip(1);

        DefaultLineMapper lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setIncludedFields(columnPositions);
        tokenizer.setNames(columnNames);

        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(FlatVehicleListing.class);

        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.setLineTokenizer(tokenizer);
        reader.setLineMapper(lineMapper);

        return reader;
    }

}
