package com.cardaddy.batch.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class CustomerColumnMappings {

    private static final String DEALIX = "Dealix";
    private static final String DETROIT_TRADING = "Detroit Trading";

    public Map<Integer, String> getCustomerMapping(String customerName) {
        Map<Integer, String> columnMappings = new TreeMap<>();

        switch (customerName) {
            case DEALIX:
                columnMappings.put(0, "customerNumber");
                columnMappings.put(1, "dealerName");
                columnMappings.put(2, "address1");
                columnMappings.put(3, "phone");
                columnMappings.put(4, "extension");
                columnMappings.put(5, "pricingTier");
                break;
            case DETROIT_TRADING:
                columnMappings.put(0, "customerNumber");
                columnMappings.put(1, "dealerName");
                columnMappings.put(2, "address1");
                columnMappings.put(5, "zipcode");
                columnMappings.put(6, "newDisclaimer");
                columnMappings.put( 7, "usedDisclaimer");
                break;
        }
        return columnMappings;
    }

}
