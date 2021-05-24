package com.sothawo.springdataelastictest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.data.elasticsearch.config.EnableReactiveElasticsearchAuditing
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories


@SpringBootApplication
@EnableElasticsearchRepositories
@EnableReactiveElasticsearchAuditing
class SpringdataElasticTestApplication {

    @Autowired
    private lateinit var operations: ReactiveElasticsearchOperations

    @EventListener(ApplicationReadyEvent::class)
    fun logElasticVersions() {
        (operations as ReactiveElasticsearchTemplate).logVersions().subscribe()
    }
}

fun main(args: Array<String>) {
    runApplication<SpringdataElasticTestApplication>(*args)
}


