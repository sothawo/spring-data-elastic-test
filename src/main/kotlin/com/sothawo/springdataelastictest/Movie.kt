/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "movies")
class Movie {
    @Id
    var id: String? = null
    var title: String? = null
    @Field(type = FieldType.Integer)
    var year: Int? = null
    var cast: List<String>? = null
    var genres: List<String>? = null
}
