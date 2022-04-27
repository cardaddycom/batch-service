package com.cardaddy.batch.service;

import org.springframework.stereotype.Service;

@Service
public class DataScrubber {

    public String getCondition(String condition) {
        if (condition != null && ("new".equals(condition) || "n".equals(condition))) {
            return "new";
        }
        return "used";
    }

    public String getTransmission(String transmission) {
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

    public String getColor(String color) {
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
        return null;
    }

}
