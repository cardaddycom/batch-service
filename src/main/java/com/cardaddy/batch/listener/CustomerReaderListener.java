package com.cardaddy.batch.listener;

import com.cardaddy.batch.model.FlatCustomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;

@Slf4j
public class CustomerReaderListener implements ItemReadListener<FlatCustomer> {

    @Override
    public void beforeRead() {
        log.info("beforeRead");
    }

    @Override
    public void afterRead(FlatCustomer customer) {
        log.info("afterRead: {}", customer.toString());
    }

    @Override
    public void onReadError(Exception e) {
        log.info("onReadError");
    }
}
