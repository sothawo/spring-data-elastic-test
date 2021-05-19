/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.person;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class Car {
    @Field(type = FieldType.Text)
    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Car{" +
            "model='" + model + '\'' +
            '}';
    }

    public static class ElectricCar extends Car {
        @Field(type = FieldType.Integer)
        private Integer range;

        public Integer getRange() {
            return range;
        }

        public void setRange(Integer range) {
            this.range = range;
        }

        @Override
        public String toString() {
            return "ElectricCar{" +
                "range=" + range +
                "} " + super.toString();
        }
    }

    public static class CombustionCar extends  Car {
        @Field(type = FieldType.Keyword)
        private String fuelType;

        public String getFuelType() {
            return fuelType;
        }

        public void setFuelType(String fuelType) {
            this.fuelType = fuelType;
        }

        @Override
        public String toString() {
            return "CombustionCar{" +
                "fuelType='" + fuelType + '\'' +
                "} " + super.toString();
        }
    }
}
