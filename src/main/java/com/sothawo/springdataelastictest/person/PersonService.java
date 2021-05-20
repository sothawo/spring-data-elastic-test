/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.person;

import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Service
public class PersonService {

    private final PersonRepository repository;
    private final ReactiveElasticsearchOperations operations;

    public PersonService(PersonRepository repository, ReactiveElasticsearchOperations operations) {
        this.repository = repository;
        this.operations = operations;
    }

    public Mono<Void> create(int count) {
        return repository.deleteAll()
            .then(Flux.range(1, count)
                .window(1000)
                .map(ids -> ids.map(Person::create))
                .flatMap(repository::saveAll)
                .then());
    }

    public Flux<Person> all() {
        return repository.findAll();
    }

    public Flux<Person> allWithAge() {

        var query = Query.findAll();
        query.addFields("age");
        query.addSourceFilter(new FetchSourceFilterBuilder().withIncludes("*").build());

        return operations.search(query, Person.class).map(SearchHit::getContent);
    }

}
