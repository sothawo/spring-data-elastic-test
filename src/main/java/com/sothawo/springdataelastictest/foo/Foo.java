/*
 * (c) Copyright 2023 sothawo
 */
package com.sothawo.springdataelastictest.foo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.lang.Nullable;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "foo")
public class Foo {
    @Id
    @Nullable
    private String id;
    @Nullable
    private String text;

    @Nullable
    @Field(type = FieldType.Long)
    private Long longValue;

    public static Foo of(int id) {
        var foo = new Foo();
        foo.setId("" + id);
        foo.setText("text-" + id);
        return foo;
    }

    @Nullable
    public String getId() {
        return id;
    }

    public void setId(@Nullable String id) {
        this.id = id;
    }

    @Nullable
    public String getText() {
        return text;
    }

    public void setText(@Nullable String text) {
        this.text = text;
    }

    @Nullable
    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(@Nullable Long longValue) {
        this.longValue = longValue;
    }
}
