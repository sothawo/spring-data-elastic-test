/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchEntityMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.Optional;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
@Profile("rest")
public class RestClientConfig extends AbstractElasticsearchConfiguration {

	public interface PersonRepository extends ElasticsearchRepository<Person, Long> {
		Optional<Person> findByLastName(final String lastName);

		@Query(value = "{\"fuzzy\":{\"last-name\":\"?0\"}}")
		Optional<Person> findByLastNameFuzzy(final String lastName);
	}

	@Bean
	public RestClient restClient(RestHighLevelClient client) {
		return client.getLowLevelClient();
	}

	@Override
	@Bean(name = { "restHighLevelClient" })
	public RestHighLevelClient elasticsearchClient() {

		final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
				.connectedTo("localhost:9200") //
				.usingSsl(NotVerifyingSSLContext.getSslContext()) //
				.withBasicAuth("elastic", "0OM9VeF3opnSSj1DAYVH") //
				.build();

		return RestClients.create(clientConfiguration).rest();
	}

	@Bean
	@Primary
	public ElasticsearchOperations elasticsearchTemplate() {
		return elasticsearchOperations();
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
