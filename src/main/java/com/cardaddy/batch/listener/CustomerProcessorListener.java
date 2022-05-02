package com.cardaddy.batch.listener;

import com.cardaddy.batch.model.FlatCustomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;

@Slf4j
public class CustomerProcessorListener implements ItemProcessListener<FlatCustomer, FlatCustomer> {

    @Override
    public void beforeProcess(FlatCustomer vehicleListing) {
        log.info("beforeProcess");
    }

    @Override
    public void afterProcess(FlatCustomer flatCustomer, FlatCustomer flatCustomer2) {
        log.info("afterProcess: " + flatCustomer + " ---> " + flatCustomer2);
    }

    @Override
    public void onProcessError(FlatCustomer flatCustomer, Exception e) {
        log.info("onProcessError");
    }
}
