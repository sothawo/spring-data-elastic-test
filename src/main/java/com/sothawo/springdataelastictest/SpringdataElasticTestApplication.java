package com.sothawo.springdataelastictest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories
public class SpringdataElasticTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringdataElasticTestApplication.class, args);
    }

}
