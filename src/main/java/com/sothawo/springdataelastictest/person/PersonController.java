/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.person;

import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/persons")
public class PersonController {
    private final PersonRepository repository;
    private final ReactiveElasticsearchOperations operations;

    public PersonController(PersonRepository repository, ReactiveElasticsearchOperations operations) {
        this.repository = repository;
        this.operations = operations;
    }

    @GetMapping("/create/{count}")
    public Mono<Void> create(@PathVariable("count") Long count) {
        return repository.deleteAll()
            .then(Flux.range(1, Math.toIntExact(count))
                .window(1000)
                .map(ids -> ids.map(Person::create))
                .flatMap(repository::saveAll)
                .then());
    }

    @GetMapping("/all")
    public Flux<Person> all() {
        return repository.findAll();
    }

    @GetMapping("/all-with-age")
    public Flux<Person> allWithAge() {

        var query = Query.findAll();
        query.addFields("age");
        query.addSourceFilter(new FetchSourceFilterBuilder().withIncludes("*").build());

        return operations.search(query, Person.class).map(SearchHit::getContent);
    }
}
