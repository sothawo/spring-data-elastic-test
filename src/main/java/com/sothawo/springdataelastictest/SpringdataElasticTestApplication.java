package com.sothawo.springdataelastictest;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

    @SpringBootApplication
    @EnableElasticsearchRepositories
    public class SpringdataElasticTestApplication {

        public static void main(String[] args) {
            SpringApplication.run(SpringdataElasticTestApplication.class, args);
        }

        @Bean
        RestHighLevelClient elasticsearchClient() {
            final ClientConfiguration configuration = ClientConfiguration.localhost();
            RestHighLevelClient client = RestClients.create(configuration).rest();
            return client;
        }

        @Bean
        ElasticsearchRestTemplate elasticsearchTemplate() {
            return new ElasticsearchRestTemplate(elasticsearchClient());
        }
    }


