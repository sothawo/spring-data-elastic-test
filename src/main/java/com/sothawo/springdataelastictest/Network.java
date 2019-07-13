/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "networks")
public class Network implements Persistable<String> {
    @Id
    private String name;
    @Field(type = FieldType.Ip_Range)
    private String cidr;
    @CreatedDate
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private LocalDateTime created;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private LocalDateTime lastModified;
    @LastModifiedBy
    private String lastModifiedBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public String getId() {
        return getName();
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return name == null || (createdBy == null && created == null);
    }
}
