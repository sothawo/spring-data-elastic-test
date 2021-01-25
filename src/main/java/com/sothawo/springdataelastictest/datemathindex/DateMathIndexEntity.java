/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.datemathindex;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName= "<foo-{now/M{yyyy.MM}}>")
public class DateMathIndexEntity {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
