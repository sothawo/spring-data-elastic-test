/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.data.elasticsearch.config.AbstractReactiveElasticsearchConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.elasticsearch.action.support.WriteRequest.*;
import static org.elasticsearch.action.support.WriteRequest.RefreshPolicy.*;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
public class ReactiveRestClientConfig extends AbstractReactiveElasticsearchConfiguration {
    @Override
    public ReactiveElasticsearchClient reactiveElasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder() //
            .connectedTo("localhost:9200") //
            .withProxy("localhost:8080")
            // .withPathPrefix("ela")
            // .usingSsl(NotVerifyingSSLContext.getSslContext()) //
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
    @Nullable
    protected RefreshPolicy refreshPolicy() {
        return NONE;
    }

    //    @Override
//    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
//        Collection<Converter<?, ?>> converters = new ArrayList<>();
//        converters.add(StringReverseConverter.INSTANCE);
//        return new ElasticsearchCustomConversions(converters);
//    }

    enum StringReverseConverter implements Converter<String, String> {

        INSTANCE;

        @Override
        public String convert(String source) {
            return new StringBuilder(source).reverse().toString();
        }
    }

    // mvcConversionService needs this
//    @Bean
//    public ElasticsearchRestTemplate elasticsearchTemplate() {
//        return new ElasticsearchRestTemplate(RestClients.create(ClientConfiguration.localhost()).rest());
//    }
}
