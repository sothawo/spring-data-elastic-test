/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.data.elasticsearch.config.AbstractReactiveElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;

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
}
