package com.cardaddy.batch.config;

import com.cardaddy.batch.domain.listing.VehicleListing;
import com.cardaddy.batch.domain.task.imports.ImportTask;
import com.cardaddy.batch.domain.task.lookup.ImportConfiguration;
import com.cardaddy.batch.exception.MissingLocationException;
import com.cardaddy.batch.job.processors.*;
import com.cardaddy.batch.job.tasklet.DeleteVehicleTasklet;
import com.cardaddy.batch.job.tasklet.FtpGetRemoteFilesTasklet;
import com.cardaddy.batch.job.tasklet.UnZipFileTasklet;
import com.cardaddy.batch.job.writer.CustomerWriter;
import com.cardaddy.batch.job.writer.VehicleItemWriter;
import com.cardaddy.batch.listener.*;
import com.cardaddy.batch.model.FlatCustomer;
import com.cardaddy.batch.model.FlatVehicleListing;
import com.cardaddy.batch.repository.ImportConfigurationRepository;
import com.cardaddy.batch.repository.ImportTaskRepository;
import com.cardaddy.batch.service.CustomerColumnMappings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CustomerColumnMappings customerColumnMappings;

    @Value("${ftp.root}")
    private String root;

    @Bean
    public VehicleItemReaderListener vehicleReaderListener() {
        return new VehicleItemReaderListener();
    }

    @Bean
    public CustomerReaderListener customerReaderListener() {
        return new CustomerReaderListener();
    }

    @Bean
    public CustomerProcessor customerProcessor() {
        return new CustomerProcessor();
    }

    @Bean
    public VehicleItemWriterListener vehicleItemWriterListener() {
        return new VehicleItemWriterListener();
    }

    @Bean
    public VehicleItemProcessorListener vehicleItemProcessorListener() {
        return new VehicleItemProcessorListener();
    }

    @Bean
    public CustomerWriterListener customerWriterListener() {
        return new CustomerWriterListener();
    }
    @Bean
    public CustomerProcessorListener customerProcessorListener() {
        return new CustomerProcessorListener();
    }

    @Bean
    public VehicleJobExecutionListener jobExecutionListener() {
        return new VehicleJobExecutionListener();
    }

    @Bean
    @StepScope
    public VehicleItemWriter vehicleWriter() {
        return new VehicleItemWriter();
    }

    @Bean
    @StepScope
    public CustomerWriter customerWriter() {
        return new CustomerWriter();
    }

    @Bean
    @StepScope
    public FtpGetRemoteFilesTasklet ftpGetRemoteFilesTasklet() {
        return new FtpGetRemoteFilesTasklet();
    }

    @Bean
    @StepScope
    public UnZipFileTasklet unZipFileTasklet() {
        return new UnZipFileTasklet();
    }

    @Bean
    @StepScope
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
    public Step unZipFileStep() {
        return this.stepBuilderFactory.get("unZipFileTasklet")
                .tasklet(unZipFileTasklet())
                .build();
    }

    @Bean
    public Step deleteVehicleStep() {
        return this.stepBuilderFactory.get("deleteVehicleStep")
                .tasklet(deleteVehicleTasklet())
                .build();
    }

    @Bean
    public Job importVehicleJob(VehicleJobExecutionListener vehicleJobExecutionListener,
                                Step vehicleProcessingStep,
                                Step customerProcessingStep,
                                Step ftpGetRemoteFilesStep,
                                Step unZipFileStep,
                                Step deleteVehicleStep) {
        return jobBuilderFactory.get("importVehicleJob")
                .incrementer(new RunIdIncrementer())
                .listener(vehicleJobExecutionListener)
                .start(ftpGetRemoteFilesStep)
                .next(unZipFileStep)
                .next(customerProcessingStep)
                .next(vehicleProcessingStep)
                .next(deleteVehicleStep)
                .build();
    }

    @Bean
    public Step vehicleProcessingStep(FlatFileItemReader<FlatVehicleListing> vehicleReader) {
        return stepBuilderFactory.get("processVehiclesStep")
                .<FlatVehicleListing, VehicleListing>chunk(30)
                .reader(vehicleReader)
                .processor(compositeProcessor())
                .writer(vehicleWriter())
                .listener(vehicleReaderListener())
                .listener(vehicleItemWriterListener())
                .listener(vehicleItemProcessorListener())
                .build();
    }

    @Bean
    public Step customerProcessingStep(FlatFileItemReader<FlatCustomer> customerReader) {
        return stepBuilderFactory.get("processVehiclesStep")
                .<FlatCustomer, FlatCustomer>chunk(30)
                .reader(customerReader)
                .processor(customerProcessor())
                .writer(customerWriter())
                .listener(customerReaderListener())
                .listener(customerWriterListener())
                .faultTolerant()
                .skipLimit(1000)
                .skip(FlatFileParseException.class)
                .skip(MissingLocationException.class)
                .build();
    }

    @Bean
    public CompositeItemProcessor compositeProcessor() {
        List<ItemProcessor> delegates = new ArrayList<>();
        delegates.add(new BodyTypeItemProcessor());
        delegates.add(new ColorItemProcessor());
        delegates.add(new PhotoItemProcessor());
        delegates.add(new TransmissionItemProcessor());
        delegates.add(new VehicleCategoryProcessor());
        delegates.add(new VehicleListingItemProcessor());

        CompositeItemProcessor processor = new CompositeItemProcessor();
        processor.setDelegates(delegates);
        return processor;
    }

    @Bean
    @StepScope
    @Transactional(readOnly = true)
    public FlatFileItemReader<FlatCustomer> customerReader(@Value("#{jobParameters['importTaskId']}") Long importTaskId,
                                                           ImportTaskRepository importTaskRepository,
                                                           ImportConfigurationRepository importConfigurationRepository) {
        log.info("Flat File Customer Reader Job {}", importTaskId);

        ImportTask importTask = importTaskRepository.findById(importTaskId).orElseThrow(() -> new NullPointerException("Import task Id " + importTaskId + " doesn't exist"));

        Map<Integer, String> customerMapping = customerColumnMappings.getCustomerMapping(importTask.getImportSystem().getName());

        int configSize = customerMapping.size();
        int[] columnPositions = new int[configSize];
        String[] columnNames = new String[configSize];

        int count = 0;

        for (Map.Entry<Integer, String> entry : customerMapping.entrySet()) {
            Integer k = entry.getKey();
            String v = entry.getValue();
            columnPositions[count] = k;
            columnNames[count] = v;
            count = count + 1;
        }

        String filePath = String.format("%s/%s/home/%s", root, importTask.getFtpAccount().getId(), importTask.getCustomerFilename());

        FlatFileItemReader reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(filePath));
        reader.setLinesToSkip(1);
        reader.setStrict(false);

        DefaultLineMapper lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setIncludedFields(columnPositions);
        tokenizer.setNames(columnNames);

        switch (importTask.getFileType().getSeparator()) {
            case "COMMA":
                tokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_COMMA);
                break;
            case "TAB":
                tokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_TAB);
                break;
            case "PIPE":
                tokenizer.setDelimiter("|");
                break;
        }

        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(FlatCustomer.class);

        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.setLineTokenizer(tokenizer);
        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    @StepScope
    @Transactional(readOnly = true)
    public FlatFileItemReader<FlatVehicleListing> vehicleReader(@Value("#{jobParameters['importTaskId']}") Long importTaskId,
                                                         ImportTaskRepository importTaskRepository,
                                                         ImportConfigurationRepository importConfigurationRepository) {
        log.info("Flat File Vehicle Reader Job {}", importTaskId);

        ImportTask importTask = importTaskRepository.getById(importTaskId);
        List<ImportConfiguration> configurationList = importConfigurationRepository.getImportConfiguration(importTask.getImportSystem().getId());

        int configSize = configurationList.size();

        int[] columnPositions = new int[configSize];
        String[] columnNames = new String[configSize];

        IntStream.range(0, configSize).forEachOrdered(i -> {
            ImportConfiguration configuration = configurationList.get(i);
            columnPositions[i] = configuration.getCsvColumnPosition();
            columnNames[i] = configuration.getImportField().getName();
        });

        String filePath = String.format("%s/%s/home/%s", root, importTask.getFtpAccount().getId(), importTask.getFilename());

        FlatFileItemReader reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(filePath));
        reader.setLinesToSkip(1);

        DefaultLineMapper lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setIncludedFields(columnPositions);
        tokenizer.setNames(columnNames);

        switch (importTask.getFileType().getSeparator()) {
            case "COMMA":
                tokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_COMMA);
                break;
            case "TAB":
                tokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_TAB);
                break;
            case "PIPE":
                tokenizer.setDelimiter("|");
                break;
        }

        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(FlatVehicleListing.class);

        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.setLineTokenizer(tokenizer);
        reader.setLineMapper(lineMapper);

        return reader;
    }

}
