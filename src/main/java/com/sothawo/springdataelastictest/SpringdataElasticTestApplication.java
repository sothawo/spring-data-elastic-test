package com.sothawo.springdataelastictest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.annotation.PostConstruct;

@SpringBootApplication(exclude = ElasticsearchDataAutoConfiguration.class)
@EnableElasticsearchRepositories
public class SpringdataElasticTestApplication {

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    public static void main(String[] args) {
        SpringApplication.run(SpringdataElasticTestApplication.class, args);
    }

    @PostConstruct
    void testMethod() {
        elasticsearchOperations.createIndex(Child.class);
        elasticsearchOperations.putMapping(Child.class);
    }
    @Document(indexName="foo")
     static class Parent  {
        @Field(type = FieldType.Nested, ignoreFields = {"child"})
        private Child child;

    }
    @Document(indexName="foo")
    static class Child extends Parent {
        private String name;
    }
}
