/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
@Profile("rest")
public class RestClientConfig extends AbstractElasticsearchConfiguration {
	@Override
	public RestHighLevelClient elasticsearchClient() {
		return RestClients.create(ClientConfiguration.localhost()).rest();
	}

	@Bean
	@Primary
	public ElasticsearchRestTemplate elasticsearchTemplate() {
		return (ElasticsearchRestTemplate) elasticsearchOperations();
	}
}
