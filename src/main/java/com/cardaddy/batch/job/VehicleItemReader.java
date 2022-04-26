package com.cardaddy.batch.job;

import com.cardaddy.batch.model.FlatVehicleListing;
import com.cardaddy.batch.repository.ImportTaskRepository;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

public class VehicleItemReader implements ItemReader<FlatVehicleListing> {

    @Autowired
    private ImportTaskRepository respository;

//    private Iterator<CreditCard> usersIterator;

    @BeforeStep
    public void before(StepExecution stepExecution) {
//        usersIterator = respository.findAll().iterator();
    }

    @Override
    public FlatVehicleListing read() {
//        if (usersIterator != null && usersIterator.hasNext()) {
//            return usersIterator.next();
//        } else {
            return null;
//        }
    }

    //    @Bean
//    public FlatFileItemReader reader() {
//        log.info("Flat File Item Reader");
////        ImportTask importTask = importTaskRepository.getById(jobId);
////        log.debug("importTask {}", importTask.getFilename());
//
//        FlatFileItemReader reader = new FlatFileItemReader<>();
//        reader.setResource(new ClassPathResource("homenetinc.csv"));
//        reader.setLinesToSkip(1);
//
//        DefaultLineMapper lineMapper = new DefaultLineMapper<>();
//        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
//        tokenizer.setNames("firstName", "lastName");
//
//        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//        fieldSetMapper.setTargetType(FlatVehicleListing.class);
//
//        lineMapper.setFieldSetMapper(fieldSetMapper);
//        lineMapper.setLineTokenizer(tokenizer);
//        reader.setLineMapper(lineMapper);
//
//        return reader;
//    }
}
