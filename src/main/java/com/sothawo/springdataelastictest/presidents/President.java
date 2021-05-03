/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.presidents;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "presidents")
public record President(
    @Id
    String id,

    @Field(type = FieldType.Text)
    String name,

    @Field(type = FieldType.Integer_Range)
    Term term) {

    @PersistenceConstructor
    public President {
    }

    public President(String name, Term term) {
        this(UUID.randomUUID().toString(), name, term);
    }

    static President of(String name, Integer from, Integer to) {
        return new President(name, new Term(from, to));
    }
}
