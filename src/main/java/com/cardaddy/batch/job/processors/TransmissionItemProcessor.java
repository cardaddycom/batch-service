package com.cardaddy.batch.job.processors;

import com.cardaddy.batch.model.FlatVehicleListing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class TransmissionItemProcessor implements ItemProcessor<FlatVehicleListing, FlatVehicleListing> {

    @Override
    public FlatVehicleListing process(FlatVehicleListing listing) throws Exception {
        log.info("Transmission Item Processor");
        listing.setTransmission(getTransmission(listing.getTransmission()));
        return listing;
    }

    public static final String getTransmission(String transmission) {
        if (transmission != null) {
            transmission = transmission.trim().toLowerCase();

            if (transmission.contains("manual") || transmission.equals("m")) {
                return "manual";
            } else if (transmission.contains("variable") || transmission.equals("v")) {
                return "variable";
            } else if (transmission.contains("cvt") || transmission.equals("c")) {
                return "cvt";
            } else if (transmission.contains("automatic") || transmission.equals("a") || transmission.equals("auto")) {
                return "automatic";
            }
            return transmission;
        }
        return null;
    }

}
