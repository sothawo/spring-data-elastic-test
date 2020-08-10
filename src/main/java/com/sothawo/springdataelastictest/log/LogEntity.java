/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.log;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
// use SpEL with a standard java type
@Document(indexName = "log-#{T(java.time.LocalDate).now().toString()}", createIndex = false)
//@Document(indexName = "log-#{T(java.lang.Runtime).getRuntime().exec(new String[]{'/bin/rm', '/tmp/somefile'})}", createIndex = true)

// use a custom bean
//@Document(indexName = "log-#{@logIndexNameProvider.timeSuffix()}", createIndex = true)

// configuration from environment
//@Document(indexName = "#{@environment.getProperty('index.prefix')}-log", createIndex = true)
public class LogEntity {
    @Id
    private String id = UUID.randomUUID().toString();

    @Field(type = FieldType.Text)
    private String text;

    @Field(name = "log-time", type = FieldType.Date, format = DateFormat.basic_date_time)
    private ZonedDateTime logTime = ZonedDateTime.now();

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ZonedDateTime getLogTime() {
        return logTime;
    }

    public void setLogTime(ZonedDateTime logTime) {
        this.logTime = logTime;
    }
}
