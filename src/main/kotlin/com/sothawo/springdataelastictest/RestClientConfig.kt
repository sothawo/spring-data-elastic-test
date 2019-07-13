/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest

import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.RestClients
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration
import java.time.Duration

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
class RestClientConfig : AbstractElasticsearchConfiguration() {

    // needed for actuator
    @Bean
    fun restClient(client: RestHighLevelClient): RestClient {
        return client.lowLevelClient
    }

    @Primary
    @Bean // (name = { "restHighLevelClient" })
    override fun elasticsearchClient(): RestHighLevelClient {

        val clientConfiguration = ClientConfiguration.builder() //
            .connectedTo("localhost:9200") //
            // .usingSsl(NotVerifyingSSLContext.getSslContext()) //
            .withBasicAuth("elastic", "stHfzUWETvvX9aAacSTW") //
            .withSocketTimeout(Duration.ofSeconds(60)).build()

        return RestClients.create(clientConfiguration).rest()
    }
}
