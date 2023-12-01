/*
 * (c) Copyright 2023 sothawo
 */
package com.sothawo.springdataelastictest.foo;

import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface FooRepository extends ReactiveElasticsearchRepository<Foo, String> {

    Flux<SearchHit<Foo>> searchBy(Sort sort);
}
