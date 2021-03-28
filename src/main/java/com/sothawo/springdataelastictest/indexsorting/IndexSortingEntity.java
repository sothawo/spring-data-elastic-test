/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.indexsorting;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.lang.Nullable;

import java.time.Instant;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "indexsorting")
@Setting(sortFields = "creationTime", sortOrders = Setting.SortOrder.asc)
public class IndexSortingEntity {
    @Nullable
    @Id
    private String id;

    @Field(name = "creation_time", type = FieldType.Date, format = DateFormat.basic_date_time)
    private Instant creationTime = Instant.now();

    @Nullable
    @Field(type = FieldType.Text)
    private String text;

    @Nullable
    public String getId() {
        return id;
    }

    public void setId(@Nullable String id) {
        this.id = id;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    @Nullable
    public String getText() {
        return text;
    }

    public void setText(@Nullable String text) {
        this.text = text;
    }
}
