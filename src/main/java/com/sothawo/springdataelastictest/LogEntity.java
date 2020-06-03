/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.ZonedDateTime;
import java.util.Date;
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

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private ZonedDateTime logTime = ZonedDateTime.now();

    private Date legacyDate = new Date();

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getLegacyDate() {
        return legacyDate;
    }

    public void setLegacyDate(Date legacyDate) {
        this.legacyDate = legacyDate;
    }

    public ZonedDateTime getLogTime() {
        return logTime;
    }

    public void setLogTime(ZonedDateTime logTime) {
        this.logTime = logTime;
    }
}
