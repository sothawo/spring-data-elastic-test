/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.presidents;

import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
data class Term(
    @Field(name = "gte")
    val from: Int,
    @Field(name = "lte")
    val to: Int
)
