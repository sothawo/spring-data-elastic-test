/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.mapping.model.CamelCaseSplittingFieldNamingStrategy;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
public class ReactiveRestClientConfig extends ReactiveElasticsearchConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveRestClientConfig.class);

	@Bean
	public ClientConfiguration clientConfiguration() {
		return ClientConfiguration.builder() //
			.connectedTo("localhost:9200") //
//            .usingSsl()
//             .usingSsl(NotVerifyingSSLContext.getSslContext()) //
			.withProxy("localhost:8080")
//            .withPathPrefix("ela")
			.withBasicAuth("elastic", "hcraescitsale") //
			.withClientConfigurer(ElasticsearchClients.ElasticsearchClientConfigurationCallback.from(webClient -> {
				LOGGER.info("Callback 1: I could now configure a {}", webClient.getClass().getName());
				return webClient;
			}))
			.withClientConfigurer(ElasticsearchClients.ElasticsearchClientConfigurationCallback.from(webClient -> {
				LOGGER.info("Callback 2: I could now configure a {}", webClient.getClass().getName());
				return webClient;
			}))
			.withHeaders(() -> {
				HttpHeaders headers = new HttpHeaders();
				headers.add("currentTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				return headers;
			})
			.build();
	}

	@Override
	protected RefreshPolicy refreshPolicy() {
		return RefreshPolicy.IMMEDIATE;
	}

	@Override
	protected FieldNamingStrategy fieldNamingStrategy() {
		return new KebabCaseFieldNamingStrategy();
	}

	static class KebabCaseFieldNamingStrategy extends CamelCaseSplittingFieldNamingStrategy {
		public KebabCaseFieldNamingStrategy() {
			super("-");
		}
	}
}
