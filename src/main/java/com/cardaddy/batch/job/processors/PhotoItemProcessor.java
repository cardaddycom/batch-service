package com.cardaddy.batch.job.processors;

import com.cardaddy.batch.model.FlatVehicleListing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class PhotoItemProcessor implements ItemProcessor<FlatVehicleListing, FlatVehicleListing> {

    @Override
    public FlatVehicleListing process(FlatVehicleListing flatVehicleListing) throws Exception {
        log.info("Photo Item Processor");
        if("".equals(flatVehicleListing.getPhotoURLs())) {
            flatVehicleListing.setPhotoURLs(null);
        }
        return flatVehicleListing;
    }

}
