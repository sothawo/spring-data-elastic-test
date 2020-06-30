/*
 * (c) Copyright 2020 codecentric AG
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.query.SeqNoPrimaryTerm;
import org.springframework.lang.Nullable;

@Document(indexName = "sample-entities")
public class SampleEntity {
    @Id
    @Nullable
    private String id;

    @Field(type = FieldType.Text)
    private String message;

    private SeqNoPrimaryTerm seqNoPrimaryTerm;

    @Nullable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SeqNoPrimaryTerm getSeqNoPrimaryTerm() {
        return seqNoPrimaryTerm;
    }

    public void setSeqNoPrimaryTerm(SeqNoPrimaryTerm seqNoPrimaryTerm) {
        this.seqNoPrimaryTerm = seqNoPrimaryTerm;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
