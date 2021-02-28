package com.sothawo.springdataelastictest.so

import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */

interface SORepository : ElasticsearchRepository<TopLevelObject, String>

@RestController
@RequestMapping("/so")
class SOController(private val operations: ElasticsearchOperations,
                   private val repository: SORepository) {

    @PostMapping("/mapping")
    fun writeMapping() {
        operations.indexOps(TopLevelObject::class.java).putMapping();
    }
}
