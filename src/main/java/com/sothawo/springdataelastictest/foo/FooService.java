/*
 * (c) Copyright 2023 sothawo
 */
package com.sothawo.springdataelastictest.foo;

import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Service
public class FooService {
    private final FooRepository repository;

    public FooService(FooRepository repository) {
        this.repository = repository;
    }

    public Flux<SearchHit<Foo>> test() {
        var foo = new Foo();
        foo.setId("1");
        foo.setLongValue(42L);
        return repository.save(foo)
                .thenMany(repository.searchBy(Sort.by("longValue")));
    }
}
