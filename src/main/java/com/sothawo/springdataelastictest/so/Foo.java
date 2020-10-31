package com.sothawo.springdataelastictest.so;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "foo")
public class Foo {
    @Id
    private String id;

    @Field(type = FieldType.Object)
    Address work;

    boolean workAddressSameAsHome;

    @Field(type = FieldType.Object)
    Address home;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Address getWork() {
        return work;
    }

    public void setWork(Address work) {
        this.work = work;
    }

    public boolean isWorkAddressSameAsHome() {
        return workAddressSameAsHome;
    }

    public void setWorkAddressSameAsHome(boolean workAddressSameAsHome) {
        this.workAddressSameAsHome = workAddressSameAsHome;
    }

    public Address getHome() {
        return home;
    }

    public void setHome(Address home) {
        this.home = home;
    }

    static class Address {
        String address1, country, state, zip;

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }
    }
}
