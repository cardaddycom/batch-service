package com.cardaddy.batch.listener;

import com.cardaddy.batch.model.FlatVehicleListing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

@Slf4j
public class VehicleItemWriterListener implements ItemWriteListener<FlatVehicleListing> {

    @Override
    public void beforeWrite(List<? extends FlatVehicleListing> list) {
        log.info("beforeWrite");
    }

    @Override
    public void afterWrite(List<? extends FlatVehicleListing> list) {
        log.info("afterWrite {}", list);
    }

    @Override
    public void onWriteError(Exception e, List<? extends FlatVehicleListing> list) {
        log.info("onWriteError");
    }
}
