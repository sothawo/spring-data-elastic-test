package com.sothawo.springdataelastictest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.elasticsearch.config.EnableReactiveElasticsearchAuditing
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

@SpringBootApplication
@EnableElasticsearchRepositories
@EnableReactiveElasticsearchAuditing
class SpringdataElasticTestApplication

fun main(args: Array<String>) {
    runApplication<SpringdataElasticTestApplication>(*args)
}


