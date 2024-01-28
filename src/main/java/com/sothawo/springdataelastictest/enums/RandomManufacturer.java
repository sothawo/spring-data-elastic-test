package com.sothawo.springdataelastictest.enums;

public class RandomManufacturer {
    public Manufacturer get() {
        // return a random value from the Manufacturere enum class
        var manufacturers = Manufacturer.values();
        return manufacturers[(int) (Math.random() * manufacturers.length)];
    }
}
