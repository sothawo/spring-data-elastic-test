/*
 * (c) Copyright 2020 codecentric AG
 */
package com.sothawo.springdataelastictest.presidents;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;

public interface PresidentRepository extends ReactiveElasticsearchRepository<President, String> {
    Flux<SearchHit<President>> searchByTerm(Integer year);
}
