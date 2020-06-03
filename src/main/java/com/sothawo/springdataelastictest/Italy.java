/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class Italy {

    @Document(indexName = "italy")
    static class City {

        @Id
        private String name;

        @Field(type = FieldType.Nested)
        private Collection<House> houses = new ArrayList<>();

        @Transient
        private final Map<House, House> housesMap = new LinkedHashMap<>();

        public City() {
        }

        public City(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Collection<House> getHouses() {
            return houses;
        }

        public void setHouses(Collection<House> houses) {
            this.houses = houses;
        }

        public Map<House, House> getHousesMap() {
            return housesMap;
        }

        public void close() {
            houses.addAll(housesMap.keySet());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            City city = (City) o;
            return Objects.equals(name, city.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    static class House {

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

    static class Inhabitant {
        @Field(type = FieldType.Text)
        private String firstName;

        @Field(type = FieldType.Text)
        private String lastName;

        public Inhabitant() {
        }

        public Inhabitant(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        @Override
        public String toString() {
            return "People{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
        }
    }
}
