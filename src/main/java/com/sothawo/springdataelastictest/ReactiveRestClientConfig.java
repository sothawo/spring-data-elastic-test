/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.data.elasticsearch.config.AbstractReactiveElasticsearchConfiguration;
import org.springframework.data.mapping.model.CamelCaseSplittingFieldNamingStrategy;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
public class ReactiveRestClientConfig extends AbstractReactiveElasticsearchConfiguration {

    @Override
    @Bean
    public ReactiveElasticsearchClient reactiveElasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder() //
            .connectedTo("localhost:9200") //
//            .usingSsl()
//             .usingSsl(NotVerifyingSSLContext.getSslContext()) //
            .withProxy("localhost:8080")
//            .withPathPrefix("ela")
            // .withBasicAuth("elastic", "stHfzUWETvvX9aAacSTW") //
            .withWebClientConfigurer(webClient -> {
                ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                    .codecs(configurer -> configurer.defaultCodecs()
                        .maxInMemorySize(-1))
                    .build();
                return webClient.mutate().exchangeStrategies(exchangeStrategies).build();
            })
            .withHeaders(() -> {
                HttpHeaders headers = new HttpHeaders();
                headers.add("currentTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                return headers;
            })
            .build();
        return ReactiveRestClients.create(clientConfiguration);

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
