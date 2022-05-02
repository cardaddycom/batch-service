package com.cardaddy.batch.model;

import lombok.Data;

@Data
public class FlatCustomer {

    private String customerNumber;
    private String dealerName;
    private String address1;
    private String zipcode;
    private String phone;
    private String pricingTier;
    private String extension;
    private String newDisclaimer;
    private String usedDisclaimer;

    private String profileDomain;

}
