/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.join;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface StatementRepository extends ReactiveElasticsearchRepository<Statement, String> {
	Flux<SearchHit<Statement>> searchAllBy();
}
