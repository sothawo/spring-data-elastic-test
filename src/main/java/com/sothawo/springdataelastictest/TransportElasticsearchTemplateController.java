/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Profile("transport")
@RestController
@RequestMapping("/template")
public class TransportElasticsearchTemplateController extends ElasticsearchBaseTemplateController{

    public TransportElasticsearchTemplateController(final ElasticsearchTemplate elasticsearchTemplate) {
        super(elasticsearchTemplate);
    }

    @PostMapping("/person")
    public String save(@RequestBody Person person) {
        return super.save(person);
    }


    @GetMapping("/person/{id}")
    public Person findById(@PathVariable("id") final Long id) {
        return super.findById(id);
    }
}
