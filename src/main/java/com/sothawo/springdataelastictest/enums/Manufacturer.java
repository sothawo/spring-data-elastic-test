package com.sothawo.springdataelastictest.enums;

import org.springframework.lang.Nullable;

public enum Manufacturer {
    YAMAHA("Yamaha", "Japan"),
    TAKAMINE("Takamine", "Japan"),
    FENDER("Fender", "United States"),
    GUILD("Guild", "United States"),
    HOEFNER("Höfner", "Germany"),
    TANGLEWOOD("Tanglewood", "United Kingdom");

    private final String displayName;
    private final String country;

    Manufacturer(String displayName, String country) {
        this.displayName = displayName;
        this.country = country;
    }

    @Nullable
    public static Manufacturer of(String displayName) {
        for (Manufacturer manufacturer : values()) {
            if (manufacturer.getDisplayName().equals(displayName)) {
                return manufacturer;
            }
        }
        return null;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCountry() {
        return country;
    }
}
