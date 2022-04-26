package com.cardaddy.batch.listener;

import com.cardaddy.batch.model.FlatVehicleListing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;

@Slf4j
public class VehicleItemProcessorListener implements ItemProcessListener<FlatVehicleListing, FlatVehicleListing> {

    @Override
    public void beforeProcess(FlatVehicleListing vehicleListing) {
        log.info("beforeProcess");
    }

    @Override
    public void afterProcess(FlatVehicleListing vehicleListing, FlatVehicleListing vehicleListing2) {
        log.info("afterProcess: " + vehicleListing + " ---> " + vehicleListing2);
    }

    @Override
    public void onProcessError(FlatVehicleListing vehicleListing, Exception e) {
        log.info("onProcessError");
    }
}
