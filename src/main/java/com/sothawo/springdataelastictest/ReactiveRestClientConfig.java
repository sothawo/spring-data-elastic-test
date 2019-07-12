/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.data.elasticsearch.config.AbstractReactiveElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchEntityMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
@Profile("reactive")
public class ReactiveRestClientConfig extends AbstractReactiveElasticsearchConfiguration {
	@Override
	public ReactiveElasticsearchClient reactiveElasticsearchClient() {
		final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
				.connectedTo("localhost:9200") //
				.usingSsl(NotVerifyingSSLContext.getSslContext()) //
				.withBasicAuth("elastic", "0OM9VeF3opnSSj1DAYVH") //
				.build();
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

	// use the ElasticsearchEntityMapper
	@Bean
	@Override
	public EntityMapper entityMapper() {
		ElasticsearchEntityMapper entityMapper = new ElasticsearchEntityMapper(elasticsearchMappingContext(),
				new DefaultConversionService());
		entityMapper.setConversions(elasticsearchCustomConversions());

		return entityMapper;
	}

}
