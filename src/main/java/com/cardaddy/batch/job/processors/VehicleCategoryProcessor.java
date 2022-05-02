package com.cardaddy.batch.job.processors;

import com.cardaddy.batch.model.FlatVehicleListing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.Calendar;

@Slf4j
public class VehicleCategoryProcessor implements ItemProcessor<FlatVehicleListing, FlatVehicleListing> {

    private static final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);

    @Override
    public FlatVehicleListing process(FlatVehicleListing listing) throws Exception {
        log.info("Category Item Processor");
        listing.setCategory(getCategory(listing));
        return listing;
    }

    private String getCategory(FlatVehicleListing listing) {
        //Strange situation with carsforsale where they are referring to the body as the category type
        String category = listing.getCategory() != null ? listing.getCategory() : listing.getBody();

        if ((CURRENT_YEAR - Integer.parseInt(listing.getYear())) >= 25) {
            //If a vehicle is 25 years or older we move it into the classic-car category. This doesn't work perfectly with all vehicles.
            return "classic-cars";
        }
        return getCategory(category);
    }

    public String getCategory(String category) {
        if (category != null) {
            category = category.toLowerCase();

            if (category.contains("aircraft")) {
                return "aircraft";
            } else if (category.contains("airplane")) {
                return "aircraft";
            } else if (category.contains("atv")) {
                return "atvs";
            } else if (category.contains("boat")) {
                return "boats";
            } else if (category.contains("motorcycle")) {
                return "motorcycles";
            } else if (category.contains("rv")) {
                return "rvs";
            } else if (category.contains("snowmobile")) {
                return "snowmobiles";
            } else if (category.contains("trailer")) {
                return "trailers";
            }
        }
        return "cars";
    }

}
