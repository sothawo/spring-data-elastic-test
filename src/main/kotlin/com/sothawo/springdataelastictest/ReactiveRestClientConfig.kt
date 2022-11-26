/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients.ElasticsearchRestClientConfigurationCallback
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration
import org.springframework.data.elasticsearch.core.RefreshPolicy
import org.springframework.data.elasticsearch.core.mapping.KebabCaseFieldNamingStrategy
import org.springframework.data.elasticsearch.support.HttpHeaders
import org.springframework.data.mapping.model.FieldNamingStrategy
import org.springframework.web.reactive.function.client.ExchangeStrategies
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.function.Supplier

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
class ReactiveRestClientConfig : ReactiveElasticsearchConfiguration() {
	override fun clientConfiguration(): ClientConfiguration {

		return ClientConfiguration.builder() //
			.connectedTo("localhost:9200") //
			//            .usingSsl()
			//             .usingSsl(NotVerifyingSSLContext.getSslContext()) //
			.withProxy("localhost:8080") //            .withPathPrefix("ela")
			.withBasicAuth("elastic", "hcraescitsale") //
			.withClientConfigurer(ElasticsearchRestClientConfigurationCallback.from { restClientBuilder ->
				LOGGER.info("Configuring the RestClient builder")
				restClientBuilder
			})
			.withHeaders(Supplier {
				HttpHeaders().apply {
					add("currentTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
				}
			})
			.build()
	}

	override fun refreshPolicy(): RefreshPolicy? = RefreshPolicy.IMMEDIATE

	override fun fieldNamingStrategy(): FieldNamingStrategy = KebabCaseFieldNamingStrategy()

	companion object {
		private val LOGGER = LoggerFactory.getLogger(ReactiveRestClientConfig::class.java)
	}

}
