/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.so;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "table")
public class Table extends Common {

    @Field(type = FieldType.Auto, includeInParent = true)
    private Student student;

    private String attends;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getAttends() {
        return attends;
    }

    public void setAttends(String attends) {
        this.attends = attends;
    }
}
