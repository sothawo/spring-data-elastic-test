/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest

import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/template")
class RestElasticsearchTemplateController(elasticsearchOperations: ElasticsearchOperations) : ElasticsearchBaseTemplateController(elasticsearchOperations) {

    @PostMapping("/person")
    override fun save(@RequestBody person: Person): String = super.save(person)

    @GetMapping("/person/{id}")
    override fun findById(@PathVariable("id") id: Long): Person? = super.findById(id)
}
