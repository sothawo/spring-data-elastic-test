/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.backend.elasticsearch7.client.reactive.ReactiveElasticsearchClient
import org.springframework.data.elasticsearch.backend.elasticsearch7.config.AbstractReactiveElasticsearchConfiguration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients
import org.springframework.data.elasticsearch.core.RefreshPolicy
import org.springframework.data.elasticsearch.core.mapping.KebabCaseFieldNamingStrategy
import org.springframework.data.mapping.model.FieldNamingStrategy
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.ExchangeStrategies
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.function.Supplier

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
class ReactiveRestClientConfig : AbstractReactiveElasticsearchConfiguration() {
    @Bean
    fun clientConfiguration(): ClientConfiguration {
        return ClientConfiguration.builder() //
            .connectedTo("localhost:9200") //
            //            .usingSsl()
            //             .usingSsl(NotVerifyingSSLContext.getSslContext()) //
            .withProxy("localhost:8080") //            .withPathPrefix("ela")
            .withBasicAuth("elastic", "hcraescitsale") //
					.withClientConfigurer { ReactiveRestClients.WebClientConfigurationCallback { webClient ->
						webClient.mutate().exchangeStrategies(ExchangeStrategies.builder()
							.codecs { configurer ->
								configurer.defaultCodecs().maxInMemorySize(-1)
							}
							.build()).build()
					} }
            .withHeaders(Supplier {
                HttpHeaders().apply {
                    add("currentTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                }
            })
            .build()
    }

    @Bean
    override fun reactiveElasticsearchClient(): ReactiveElasticsearchClient = ReactiveRestClients.create(clientConfiguration())

    override fun refreshPolicy(): RefreshPolicy? = RefreshPolicy.IMMEDIATE

    override fun fieldNamingStrategy(): FieldNamingStrategy = KebabCaseFieldNamingStrategy()
}
