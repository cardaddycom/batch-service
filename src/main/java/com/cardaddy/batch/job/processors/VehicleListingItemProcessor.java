package com.cardaddy.batch.job.processors;

import com.cardaddy.batch.model.FlatVehicleListing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Slf4j
public class VehicleListingItemProcessor implements ItemProcessor<FlatVehicleListing, FlatVehicleListing> {

    private static final long DETROIT_TRADER_ID = 20006;

    @Value("#{jobParameters['importTaskId']}")
    private Long importTaskId;

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
        listing.setExteriorColorCustom(listing.getExteriorColorCustom().replaceAll("[^\\x20-\\x7e]", ""));
        listing.setDetroitTrader(DETROIT_TRADER_ID == importTaskId);

        if(listing.getPhotoURLs() != null) {
            listing.setPhotoURLs(listing.getPhotoURLs().replaceAll("\\|", ","));
        }

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
