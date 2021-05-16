package com.sothawo.springdataelastictest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.AuditorAware
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional

@SpringBootApplication(exclude = [ElasticsearchDataAutoConfiguration::class])
@EnableElasticsearchRepositories
class SpringdataElasticTestApplication

fun main(args: Array<String>) {
    runApplication<SpringdataElasticTestApplication>(*args)
}


