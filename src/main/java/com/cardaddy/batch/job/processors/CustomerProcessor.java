package com.cardaddy.batch.job.processors;

import com.cardaddy.batch.model.FlatCustomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class CustomerProcessor implements ItemProcessor<FlatCustomer, FlatCustomer> {

    @Override
    public FlatCustomer process(FlatCustomer flatCustomer) throws Exception {
        log.info("Customer Item Processor");
        flatCustomer.setExtension(flatCustomer.getExtension() != null && !flatCustomer.getExtension().isEmpty() ? flatCustomer.getExtension() : null);
        return flatCustomer;
    }

    private String getPhone(FlatCustomer flatCustomer, String phone) {
        if(flatCustomer.getPhone() != null && !flatCustomer.getPhone().isEmpty()) {
            return flatCustomer.getPhone();
        }
        return phone;
    }

}
