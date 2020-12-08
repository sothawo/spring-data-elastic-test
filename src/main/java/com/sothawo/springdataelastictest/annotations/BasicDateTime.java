/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Field(type = FieldType.Date, format = DateFormat.basic_date_time)
public @interface BasicDateTime {
    @AliasFor(value = "name", annotation = Field.class)
    String name() default "";

    @AliasFor(value = "name", annotation = Field.class)
    String value() default"";
}
