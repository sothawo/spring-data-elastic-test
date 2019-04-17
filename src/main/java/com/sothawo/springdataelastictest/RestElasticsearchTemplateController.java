/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
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
public class RestElasticsearchTemplateController extends ElasticsearchBaseTemplateController {

    public RestElasticsearchTemplateController(final ElasticsearchRestTemplate elasticsearchRestTemplate) {
        super(elasticsearchRestTemplate);
    }

    @PostMapping("/person")
    public String save(@RequestBody Person person) {
        return super.save(person);
    }
}
