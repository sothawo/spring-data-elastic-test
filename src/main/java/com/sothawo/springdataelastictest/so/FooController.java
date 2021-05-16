/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.so;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/foo")
public class FooController {

    private final FooRepository fooRepository;
    private final ElasticsearchOperations operations;

    public FooController(FooRepository fooRepository, ElasticsearchOperations operations) {
        this.fooRepository = fooRepository;
        this.operations = operations;
    }

    @PostMapping
    public void add(@RequestBody Foo foo) {
        fooRepository.save(foo);
    }

    @RequestMapping("/{id}")
    public Foo get(@PathVariable String id) {
        return fooRepository.findById(id).orElse(null);
    }

    @GetMapping
    public SearchHits<Foo> all() {
        return fooRepository.searchBy();
    }
}
