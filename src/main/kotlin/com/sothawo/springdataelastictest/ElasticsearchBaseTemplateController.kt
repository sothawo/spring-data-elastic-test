/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest

import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.query.GetQuery
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
open class ElasticsearchBaseTemplateController(private val elasticsearchOperations: ElasticsearchOperations) {

    open fun save(person: Person): String {
        return elasticsearchOperations.save(person).id.toString()
    }

    open fun findById(id: Long): Person? = elasticsearchOperations.get(id.toString(), Person::class.java)

}
