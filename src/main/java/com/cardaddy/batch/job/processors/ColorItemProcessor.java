package com.cardaddy.batch.job.processors;

import com.cardaddy.batch.model.FlatVehicleListing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class ColorItemProcessor implements ItemProcessor<FlatVehicleListing, FlatVehicleListing> {

    @Override
    public FlatVehicleListing process(FlatVehicleListing listing) throws Exception {
        log.info("Color Item Processor");
        listing.setExteriorColorCustom(getColor(listing.getExteriorColor()));
        listing.setInteriorColorCustom(getColor(listing.getInteriorColor()));
        return listing;
    }

    private String getColor(String color) {
        if (color != null) {
            color = color.toLowerCase();

            if (color.contains("black")) {
                return "black";
            } else if (color.contains("blue")) {
                return "blue";
            } else if (color.contains("brown")) {
                return "brown";
            } else if (color.contains("burgundy")) {
                return "burgundy";
            } else if (color.contains("gold")) {
                return "gold";
            } else if (color.contains("gray") || color.contains("grey")) {
                return "gray";
            } else if (color.contains("green")) {
                return "green";
            } else if (color.contains("orange")) {
                return "orange";
            } else if (color.contains("purple")) {
                return "purple";
            } else if (color.contains("red")) {
                return "red";
            } else if (color.contains("silver")) {
                return "silver";
            } else if (color.contains("tan")) {
                return "tan";
            } else if (color.contains("teal")) {
                return "teal";
            } else if (color.contains("white")) {
                return "white";
            } else if (color.contains("yellow")) {
                return "yellow";
            }
        }
        return color;
    }

}
