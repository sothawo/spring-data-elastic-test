/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchEntityMapper;
import org.springframework.data.elasticsearch.core.EntityMapper;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    // needed for actuator
    @Bean
    public RestClient restClient(RestHighLevelClient client) {
        return client.getLowLevelClient();
    }

    @Override
    @Primary
    @Bean // (name = { "restHighLevelClient" })
    public RestHighLevelClient elasticsearchClient() {

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder() //
            .connectedTo("localhost:9200") //
//            .usingSsl() //
//             .usingSsl(NotVerifyingSSLContext.getSslContext()) //
            .withProxy("localhost:8080")
            // .withPathPrefix("ela") //
            //.withBasicAuth("elastic", "stHfzUWETvvX9aAacSTW") //
            // .withSocketTimeout(Duration.ofSeconds(60))
            .build();

        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    @Override
    public EntityMapper entityMapper() {

        ElasticsearchEntityMapper entityMapper = new ElasticsearchEntityMapper(
            elasticsearchMappingContext(), new DefaultConversionService()
        );
        entityMapper.setConversions(elasticsearchCustomConversions());

        return entityMapper;
    }
}
