package com.cardaddy.batch.job;

import com.cardaddy.batch.model.FlatVehicleListing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

@Slf4j
public class VehicleItemReader implements ItemReader<FlatVehicleListing> {
    @Override
    public FlatVehicleListing read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        return null;
    }
//
//    @Autowired
//    private ImportTaskRepository repository;
//
////    private Iterator<CreditCard> usersIterator;
//
//    @BeforeStep
//    public void before(StepExecution stepExecution) {
////        usersIterator = respository.findAll().iterator();
//    }
//
//    public FlatFileItemReader<FlatVehicleListing> reader() {
//        log.info("Flat File Item Reader");
//        ImportTask importTask = repository.getById(1L);
//        log.debug("importTask {}", importTask.getFilename());
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
