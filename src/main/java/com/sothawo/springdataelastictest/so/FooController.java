/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.so;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;


/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/foo")
public class FooController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FooController.class);

    private final FooRepository fooRepository;
    private final ElasticsearchOperations operations;

    public FooController(FooRepository fooRepository, ElasticsearchOperations operations) {
        this.fooRepository = fooRepository;
        this.operations = operations;
    }

    @PostMapping
    public Foo add(@RequestBody Foo foo) {
        return fooRepository.save(foo);
    }

    @RequestMapping("/{id}")
    public Foo get(@PathVariable String id) {
        return fooRepository.findById(id).orElse(null);
    }

    @GetMapping("/now")
    public Foo now() {
        var foo = new Foo();
        foo.setId("42");
        foo.setSomeDate(ZonedDateTime.now());
        return fooRepository.save(foo);
    }

    @GetMapping
    public SearchHits<Foo> all() {
        return fooRepository.searchBy();
    }

    @GetMapping("/test")
    public SearchHits<Foo> test() {
        var foo = new Foo();
        foo.setId("1");
        foo.setLongValue(42L);
        foo.setText("lemon");
        fooRepository.save(foo);

        return fooRepository.searchByFoo("lemon");
    }

    @GetMapping("/userquery/{id}")
    public SearchHits<Foo> userQuery(@PathVariable Integer id) {
        return fooRepository.getUserQuery(id);
    }

    @DeleteMapping
    public void deleteAll() {
        operations.delete(operations.matchAllQuery(), Foo.class);
    }

    @GetMapping("/generic")
    public SearchHits<GenericEntity> allGeneric() {
        var criteria = Criteria.where("need").is("alcohol");
        Query query = new CriteriaQuery(criteria);
        return operations.search(query, GenericEntity.class, IndexCoordinates.of("foo"));
    }
}
