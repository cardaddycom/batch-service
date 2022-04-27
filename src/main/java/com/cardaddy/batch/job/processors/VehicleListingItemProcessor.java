package com.cardaddy.batch.job.processors;

import com.cardaddy.batch.model.FlatVehicleListing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.Date;

@Slf4j
public class VehicleListingItemProcessor implements ItemProcessor<FlatVehicleListing, FlatVehicleListing> {

    @Override
    public FlatVehicleListing process(FlatVehicleListing flatVehicleListing) throws Exception {
        log.info("Vehicle Listing Item Processor {}", flatVehicleListing.getVin());

        flatVehicleListing.setVin(flatVehicleListing.getVin().toUpperCase());
        flatVehicleListing.setSchedulerDate(new Date());
        flatVehicleListing.setCategory("cars");

        return flatVehicleListing;
    }

}
