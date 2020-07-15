/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface LogRepository extends ReactiveElasticsearchRepository<LogEntity, String> {

    Flux<SearchHit<LogEntity>> searchByText(String text);
}
