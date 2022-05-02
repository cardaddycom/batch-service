package com.cardaddy.batch.listener;

import com.cardaddy.batch.model.FlatCustomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

@Slf4j
public class CustomerWriterListener implements ItemWriteListener<FlatCustomer> {

    @Override
    public void beforeWrite(List<? extends FlatCustomer> list) {
        log.info("beforeWrite");
    }

    @Override
    public void afterWrite(List<? extends FlatCustomer> list) {
//        log.info("afterWrite {}", list);
        log.info("afterWrite");
    }

    @Override
    public void onWriteError(Exception e, List<? extends FlatCustomer> list) {
        log.info("onWriteError");
    }

}
