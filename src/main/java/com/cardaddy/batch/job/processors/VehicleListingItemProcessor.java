package com.cardaddy.batch.job.processors;

import com.cardaddy.batch.model.FlatVehicleListing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.Date;

@Slf4j
public class VehicleListingItemProcessor implements ItemProcessor<FlatVehicleListing, FlatVehicleListing> {

    @Override
    public FlatVehicleListing process(FlatVehicleListing listing) throws Exception {
        log.info("Vehicle Listing Item Processor {}", listing.getVin());

        listing.setVin(listing.getVin().toUpperCase());
        listing.setSchedulerDate(new Date());
        listing.setVehicleTitle(buildVehicleTitle(listing));
        listing.setCondition(getCondition(listing.getCondition()));
        listing.setStockNumber(listing.getStockNumber() != null ? listing.getStockNumber().toUpperCase() : null);

        listing.setDescription(listing.getDescription().replaceAll("[\\u0000-\\uFFFF]", ""));
        listing.setDescription(listing.getDescription().replaceAll("[^\\x20-\\x7e]", ""));
        listing.setDescription(listing.getDescription().replaceAll("\\xFFFD", ""));

        listing.setOptions(listing.getOptions().replaceAll("[\\u0000-\\uFFFF]", ""));
        listing.setOptions(listing.getOptions().replaceAll("\\xFFFD", ""));
        listing.setOptions(listing.getOptions().replaceAll("[^\\x20-\\x7e]", ""));

        listing.setInteriorColorCustom(listing.getInteriorColorCustom().replaceAll("[^\\x20-\\x7e]", ""));

        return listing;
    }

    private String buildVehicleTitle(FlatVehicleListing listing) {
        if(listing.getVehicleTitle() != null) {
            return listing.getVehicleTitle();
        }
        return String.format("%s %s %s", listing.getYear(), listing.getMake(), listing.getModel()) + listing.getTrim() != null ? listing.getTrim() : "";
    }

    public static final String getCondition(String condition) {
        if (condition != null && ("new".equals(condition) || "n".equals(condition))) {
            return "new";
        }
        return "used";
    }

}
