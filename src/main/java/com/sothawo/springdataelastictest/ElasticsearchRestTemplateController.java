/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Profile("rest")
@RestController
@RequestMapping("/template")
public class ElasticsearchRestTemplateController {

    // uses RestClient
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public ElasticsearchRestTemplateController(final ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    @PostMapping("/person")
    public String hello(@RequestBody Person person) {

        final IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(person.getId().toString())
                .withObject(person)
                .build();

        final String documentId = elasticsearchRestTemplate.index(indexQuery);

        return documentId;
    }
}
