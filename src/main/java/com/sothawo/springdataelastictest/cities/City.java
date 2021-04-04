/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.cities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "cities")
class City {

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
