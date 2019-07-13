/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.http.HttpHeaders;

import javax.crypto.spec.RC2ParameterSpec;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        defaultHeaders.add("header-name", "header-value");

        Supplier<HttpHeaders> currentTimeHeaders = () -> {
            HttpHeaders headers = new HttpHeaders();
            headers.add("currentTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            return headers;
        };

        ClientConfiguration clientConfiguration = ClientConfiguration.builder() //
            .connectedTo("localhost:9200") //
//            .usingSsl()
//            .usingSsl(NotVerifyingSSLContext.getSslContext()) //
            .withProxy("localhost:8080")
            .withPathPrefix("ela")
//            .withBasicAuth("elastic", "stHfzUWETvvX9aAacSTW") //
//            .withDefaultHeaders(defaultHeaders)
            .withHeaders(currentTimeHeaders)
//            .withConnectTimeout(Duration.ofSeconds(10))
//            .withSocketTimeout(Duration.ofSeconds(1)) //
            .build();

        return RestClients.create(clientConfiguration).rest();
    }

//    @Override
//    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
//        Collection<Converter<?, ?>> converters = new ArrayList<>();
//        converters.add(StringReverseConverter.INSTANCE);
//        return new ElasticsearchCustomConversions(converters);
//    }


    @Override
    public ElasticsearchOperations elasticsearchOperations(ElasticsearchConverter elasticsearchConverter) {
        return new ElasticsearchRestTemplate(elasticsearchClient(), elasticsearchConverter) {
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
    }
}
