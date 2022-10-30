/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchDateConverter;
import org.springframework.data.elasticsearch.core.mapping.KebabCaseFieldNamingStrategy;
import org.springframework.data.elasticsearch.support.HttpHeaders;
import org.springframework.data.mapping.model.FieldNamingStrategy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
public class RestClientConfig extends ElasticsearchConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestClientConfig.class);

	@Override
	public ClientConfiguration clientConfiguration() {

		HttpHeaders defaultHeaders = new HttpHeaders();
//		defaultHeaders.add("Accept", "application/vnd.elasticsearch+json;compatible-with=7");
//		defaultHeaders.add("Content-Type", "application/vnd.elasticsearch+json;compatible-with=7");

		Supplier<HttpHeaders> currentTimeHeaders = () -> {
			HttpHeaders headers = new HttpHeaders();
			headers.add("x-current-time", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
			return headers;
		};

		return ClientConfiguration.builder() //

			// Elasticsearch
			.connectedTo("localhost:9200") //
			.withBasicAuth("elastic", "hcraescitsale") //
//			.withDefaultHeaders(defaultHeaders) //

			// OpenSearch
//			.connectedTo("localhost:9400") //
//			.usingSsl(NotVerifyingSSLContext.getSslContext()) //
//			.withBasicAuth("admin", "admin") //

//			.usingSsl()
//			.usingSsl(NotVerifyingSSLContext.getSslContext()) //

			.withProxy("localhost:8080")
			.withHeaders(currentTimeHeaders)
//            .withConnectTimeout(Duration.ofSeconds(10))
//            .withSocketTimeout(Duration.ofSeconds(1)) //
			.withClientConfigurer(ElasticsearchClients.ElasticsearchRestClientConfigurationCallback.from(clientBuilder -> {
				LOGGER.info("Callback 1: I could now configure a {}", clientBuilder.getClass().getName());
				return clientBuilder;
			}))
			.withClientConfigurer(ElasticsearchClients.ElasticsearchHttpClientConfigurationCallback.from(clientBuilder -> {
				LOGGER.info("Callback 2: I could now configure a {}", clientBuilder.getClass().getName());
				return clientBuilder;
			}))
			.build();
	}

	@Override
	public ElasticsearchCustomConversions elasticsearchCustomConversions() {
		Collection<Converter<?, ?>> converters = new ArrayList<>();
		converters.add(new LocalDateTimeConverter());
		return new ElasticsearchCustomConversions(converters);
	}

	@Override
	protected RefreshPolicy refreshPolicy() {
		return RefreshPolicy.IMMEDIATE;
	}

	@Override
	protected FieldNamingStrategy fieldNamingStrategy() {
		return new KebabCaseFieldNamingStrategy();
	}

	@WritingConverter
	static class LocalDateTimeConverter implements Converter<LocalDateTime, String> {

		private final ElasticsearchDateConverter converter = ElasticsearchDateConverter.of("uuuu-MM-d'T'HH:mm:ss");

		@Override
		public String convert(LocalDateTime source) {
			return converter.format(source);
		}
	}
}
