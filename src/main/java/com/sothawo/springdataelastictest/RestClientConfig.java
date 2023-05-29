/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchDateConverter;
import org.springframework.data.elasticsearch.core.mapping.KebabCaseFieldNamingStrategy;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestClientConfig.class);

	@Override
	@Bean
	public RestHighLevelClient elasticsearchClient() {

		HttpHeaders defaultHeaders = new HttpHeaders();
		defaultHeaders.add("Accept", "application/vnd.elasticsearch+json;compatible-with=7");
		defaultHeaders.add("Content-Type", "application/vnd.elasticsearch+json;compatible-with=7");

		Supplier<HttpHeaders> currentTimeHeaders = () -> {
			HttpHeaders headers = new HttpHeaders();
			headers.add("currentTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
			return headers;
		};

		ClientConfiguration clientConfiguration = ClientConfiguration.builder() //

			// Elasticsearch
			.connectedTo("localhost:9200") //
			.withBasicAuth("elastic", "hcraescitsale") //
			.withDefaultHeaders(defaultHeaders) //
//			.usingSsl()
//			.usingSsl(NotVerifyingSSLContext.getSslContext()) //
			.withProxy("localhost:8080")
			.withHeaders(currentTimeHeaders)
//            .withConnectTimeout(Duration.ofSeconds(10))
//            .withSocketTimeout(Duration.ofSeconds(1)) //
			.withClientConfigurer(RestClients.RestClientConfigurationCallback.from(clientBuilder -> {
				LOGGER.info("Callback 1: I could now configure a {}", clientBuilder.getClass().getName());
				return clientBuilder;
			}))
			.withClientConfigurer(RestClients.RestClientConfigurationCallback.from(clientBuilder -> {
				LOGGER.info("Callback 2: I could now configure a {}", clientBuilder.getClass().getName());
				return clientBuilder;
			}))
			.build();

		var restHighLevelClient = RestClients.create(clientConfiguration).rest();

		try {
			restHighLevelClient.info(RequestOptions.DEFAULT);
		} catch (IOException e) {
			LOGGER.warn("Error on first info request", e);
		} catch (ElasticsearchException e) {
			LOGGER.warn("Elasticsearch Exception", e);
			try {
				restHighLevelClient.info(RequestOptions.DEFAULT);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return restHighLevelClient;
	}

	@Override
	public ElasticsearchCustomConversions elasticsearchCustomConversions() {
		Collection<Converter<?, ?>> converters = new ArrayList<>();
		converters.add(new LocalDateTimeConverter());
		return new ElasticsearchCustomConversions(converters);
	}

/*
	@Override
	public ElasticsearchOperations elasticsearchOperations(ElasticsearchConverter elasticsearchConverter, RestHighLevelClient elasticsearchClient) {
		ElasticsearchRestTemplate template = new ElasticsearchRestTemplate(elasticsearchClient, elasticsearchConverter) {
			@Override
			public <T> T execute(ClientCallback<T> callback) {
				try {
					return super.execute(callback);
				} catch (DataAccessResourceFailureException e) {
					// retry
					LOGGER.warn("Retrying because of {}", e.getMessage());
					return super.execute(callback);
				}
			}
		};
		template.setRefreshPolicy(refreshPolicy());
		return template;
	}
*/

	@Override
	protected RefreshPolicy refreshPolicy() {
		return RefreshPolicy.WAIT_UNTIL;
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
