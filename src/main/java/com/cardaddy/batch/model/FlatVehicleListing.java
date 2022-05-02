package com.cardaddy.batch.model;

import lombok.Data;

import java.util.Date;

@Data
public class FlatVehicleListing {

    private String year;
    private String make;
    private String model;
    private String trim;
    private String sellingPrice;
    private String description;
    private String vin;
    private String stockNumber;
    private String dealerId;
    private String partnerId;
    private String body;
    private String doors;
    private String exteriorColor;
    private String interiorColor;
    private String engineCylinders;
    private String transmission;
    private String mileage;
    private String bookValue;
    private String certified;
    private String transmissionSpeed;
    private String drivetrain;
    private String fuelType;
    private String cityMPG;
    private String highwayMPG;
    private String categorizedOptions;
    private String photoURLs;
    private String condition;
    private String options;
    private String phone;
    private String distance;
    private String displacement;
    private String engineDescription;
    private String dealerLiveId;
    private String zipcode;
    private String ppcUrl;
    private String phonePayoutPrice;
    private String phoneExtension;
    private String webPayoutPrice;
    private String numberOfImages;
    private String websiteListingUrl;
    private String category;
    private String title;
    private String dateCreated;

    //Filled during the processor step
    private boolean dealix;
    private boolean detroitTrader;
    private String sellerType;
    private String inventoryType;
    private String vehicleTitle;
    private Date schedulerDate;
    private String exteriorColorCustom;
    private String interiorColorCustom;

}
