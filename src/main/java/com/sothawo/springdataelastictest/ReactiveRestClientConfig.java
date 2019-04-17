/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.data.elasticsearch.config.AbstractReactiveElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
@Profile("reactive")
public class ReactiveRestClientConfig extends AbstractReactiveElasticsearchConfiguration {
	@Override
	public ReactiveElasticsearchClient reactiveElasticsearchClient() {
		ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo("localhost:9200").build();
		return ReactiveRestClients.create(clientConfiguration);

	}

	@Bean
	public ReactiveElasticsearchTemplate reactiveElasticsearchTemplate() {
		return (ReactiveElasticsearchTemplate) super.reactiveElasticsearchTemplate();
	}

	// mvcConversionService needs this
	@Bean
	public ElasticsearchRestTemplate elasticsearchTemplate() {
		return new ElasticsearchRestTemplate(RestClients.create(ClientConfiguration.localhost()).rest(),
				elasticsearchConverter(), resultsMapper());
	}

}
