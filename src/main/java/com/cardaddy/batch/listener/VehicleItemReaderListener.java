package com.cardaddy.batch.listener;

import com.cardaddy.batch.model.FlatVehicleListing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;

@Slf4j
public class VehicleItemReaderListener implements ItemReadListener<FlatVehicleListing> {

    @Override
    public void beforeRead() {
        log.info("beforeRead");
    }

    @Override
    public void afterRead(FlatVehicleListing vehicleListing) {
        log.info("afterRead: {}", vehicleListing.toString());
    }

    @Override
    public void onReadError(Exception e) {
        log.info("onReadError");
    }
}
