package com.sothawo.springdataelastictest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;

@SpringBootApplication
@EnableReactiveElasticsearchRepositories
@EnableElasticsearchAuditing
public class SpringdataElasticTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringdataElasticTestApplication.class, args);
    }
}
