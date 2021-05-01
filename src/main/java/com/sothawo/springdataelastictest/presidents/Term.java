/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.presidents;

import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
record Term(
    @Field(name = "gte")
    Integer from,
    @Field(name = "lte")
    Integer to
) {
}
