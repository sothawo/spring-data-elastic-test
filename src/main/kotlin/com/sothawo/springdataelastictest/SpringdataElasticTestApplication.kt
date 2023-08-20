package com.sothawo.springdataelastictest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchClientAutoConfiguration
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.data.elasticsearch.core.AbstractElasticsearchTemplate
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories


@SpringBootApplication(exclude = [ElasticsearchClientAutoConfiguration::class, ElasticsearchDataAutoConfiguration::class])
@EnableElasticsearchRepositories
class SpringdataElasticTestApplication {

    @Autowired
    private lateinit var operations: ElasticsearchOperations

    @EventListener(ApplicationReadyEvent::class)
    fun logElasticVersions() {
        (operations as AbstractElasticsearchTemplate).logVersions()
    }

}

fun main(args: Array<String>) {
    runApplication<SpringdataElasticTestApplication>(*args)
}
