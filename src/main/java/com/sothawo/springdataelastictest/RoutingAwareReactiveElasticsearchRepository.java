/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Mono;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@NoRepositoryBean
public interface RoutingAwareReactiveElasticsearchRepository<T, ID> extends ReactiveElasticsearchRepository<T, ID> {
	Mono<T> findByIdWithRouting(ID id, String routing);
}
