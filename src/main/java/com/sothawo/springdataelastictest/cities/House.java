/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.cities;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
class House {

    @Field(type = FieldType.Text)
    private String street;

    @Field(type = FieldType.Text)
    private String streetNumber;

    @Field(type = FieldType.Nested)
    private List<Inhabitant> inhabitants = new ArrayList<>();

    public House() {
    }

    public House(String street, String streetNumber) {
        this.street = street;
        this.streetNumber = streetNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return Objects.equals(street, house.street) &&
            Objects.equals(streetNumber, house.streetNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, streetNumber);
    }

    @Override
    public String toString() {
        return "House{" +
            "street='" + street + '\'' +
            ", streetNumber='" + streetNumber + '\'' +
            ", inhabitants=" + inhabitants +
            '}';
    }
}
