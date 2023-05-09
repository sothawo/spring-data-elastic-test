/*
 * (c) Copyright 2019-2023 sothawo
 */
package com.sothawo.springdataelastictest

import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import org.elasticsearch.client.RestClientBuilder
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients.ElasticsearchHttpClientConfigurationCallback
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients.ElasticsearchRestClientConfigurationCallback
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration
import org.springframework.data.elasticsearch.core.RefreshPolicy
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions
import org.springframework.data.elasticsearch.core.convert.ElasticsearchDateConverter
import org.springframework.data.elasticsearch.core.mapping.KebabCaseFieldNamingStrategy
import org.springframework.data.elasticsearch.support.HttpHeaders
import org.springframework.data.mapping.model.FieldNamingStrategy
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.function.Supplier

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
class RestClientConfig : ElasticsearchConfiguration() {
		override fun clientConfiguration(): ClientConfiguration {
				val defaultHeaders = HttpHeaders()
				//		defaultHeaders.add("Accept", "application/vnd.elasticsearch+json;compatible-with=7");
				//		defaultHeaders.add("Content-Type", "application/vnd.elasticsearch+json;compatible-with=7");
				val currentTimeHeaders = Supplier {
						val headers = HttpHeaders()
						headers.add("x-current-time", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
						headers
				}
				return ClientConfiguration.builder() //
								// Elasticsearch
								.connectedTo("localhost:9200") //
								// .usingSsl("2D:82:5A:D3:9C:EB:03:E7:B9:B0:80:CE:14:00:05:FA:53:29:94:0D:48:BA:75:5E:A8:44:A7:08:21:48:ED:BA") //
								//			.usingSsl()
								//			.usingSsl(NotVerifyingSSLContext.getSslContext()) //
								.withBasicAuth("elastic", "hcraescitsale") //
								.withDefaultHeaders(defaultHeaders) //
								.withProxy("localhost:8080")
								.withHeaders(currentTimeHeaders) //
								//  .withConnectTimeout(Duration.ofSeconds(10))
								//            .withSocketTimeout(Duration.ofSeconds(1)) //
								.withClientConfigurer(ElasticsearchRestClientConfigurationCallback.from { clientBuilder: RestClientBuilder ->
										LOGGER.info("Callback 1: I could now configure a {}", clientBuilder.javaClass.name)
										clientBuilder
								})
								.withClientConfigurer(ElasticsearchHttpClientConfigurationCallback.from { clientBuilder: HttpAsyncClientBuilder ->
										LOGGER.info("Callback 2: I could now configure a {}", clientBuilder.javaClass.name)
										clientBuilder
								})
								.build()
		}

		override fun elasticsearchCustomConversions(): ElasticsearchCustomConversions {
				val converters: MutableCollection<Converter<*, *>?> = mutableListOf()
				converters += LocalDateTimeConverter()
				//		converters.add(new ManufacturerWritingConverter());
				//		converters.add(new ManufacturerReadingConverter());
				return ElasticsearchCustomConversions(converters)
		}

		override fun refreshPolicy() = RefreshPolicy.IMMEDIATE

		override fun fieldNamingStrategy() = KebabCaseFieldNamingStrategy()

		@WritingConverter
		internal class LocalDateTimeConverter : Converter<LocalDateTime?, String> {
				private val converter = ElasticsearchDateConverter.of("uuuu-MM-d'T'HH:mm:ss")

				override fun convert(source: LocalDateTime) = converter.	format(source)
		}

		companion object {
				private val LOGGER = LoggerFactory.getLogger(RestClientConfig::class.java)
		}
}
