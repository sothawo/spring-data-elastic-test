/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "logs-#{@logIndexNameProvider.timeSuffix()}")
public class LogEntity {
    @Id
    private String id = UUID.randomUUID().toString();

    @Field(type = FieldType.Text)
    private String text;

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
