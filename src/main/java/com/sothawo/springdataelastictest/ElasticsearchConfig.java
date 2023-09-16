/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import com.sothawo.springdataelastictest.enums.ManufacturerReadingConverter;
import com.sothawo.springdataelastictest.enums.ManufacturerWritingConverter;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
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

import static org.springframework.data.elasticsearch.client.elc.ElasticsearchClients.ElasticsearchHttpClientConfigurationCallback;
import static org.springframework.data.elasticsearch.client.elc.ElasticsearchClients.ElasticsearchRestClientConfigurationCallback;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

		private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchConfig.class);

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
//								.usingSsl("2D:82:5A:D3:9C:EB:03:E7:B9:B0:80:CE:14:00:05:FA:53:29:94:0D:48:BA:75:5E:A8:44:A7:08:21:48:ED:BA") //
//								.usingSsl()
//								.usingSsl(NotVerifyingSSLContext.getSslContext()) //
								.withBasicAuth("elastic", "hcraescitsale") //
//			.withDefaultHeaders(defaultHeaders) //


								.withProxy("localhost:8080")
								.withHeaders(currentTimeHeaders)
//            .withConnectTimeout(Duration.ofSeconds(10))
//            .withSocketTimeout(Duration.ofSeconds(1)) //
								.withClientConfigurer(ElasticsearchRestClientConfigurationCallback.from(clientBuilder -> {
										LOGGER.info("Callback 1: I could now configure a {}", clientBuilder.getClass().getName());
										return clientBuilder;
								}))
								.withClientConfigurer(ElasticsearchHttpClientConfigurationCallback.from(clientBuilder -> {
										LOGGER.info("Callback 2: I could now configure a {}", clientBuilder.getClass().getName());
										return clientBuilder;
								}))
								.build();
		}

		@Override
		public ElasticsearchCustomConversions elasticsearchCustomConversions() {
				Collection<Converter<?, ?>> converters = new ArrayList<>();
				converters.add(new LocalDateTimeConverter());
				converters.add(new ManufacturerWritingConverter());
				converters.add(new ManufacturerReadingConverter());
				return new ElasticsearchCustomConversions(converters);
		}

		@Override
		public ElasticsearchTransport elasticsearchTransport(RestClient restClient, JsonpMapper jsonpMapper) {
				return new DelegatingTransport(super.elasticsearchTransport(restClient, jsonpMapper));
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
