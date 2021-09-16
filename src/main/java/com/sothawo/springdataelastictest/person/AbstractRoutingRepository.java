/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.person;

import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.routing.RoutingResolver;
import reactor.core.publisher.Mono;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public abstract class AbstractRoutingRepository<T, ID> implements RoutingRepository<T, ID> {

	private final ReactiveElasticsearchOperations operations;
	private final Class<T> clazz;

	protected AbstractRoutingRepository(ReactiveElasticsearchOperations operations, Class<T> clazz) {
		this.operations = operations;
		this.clazz = clazz;
	}

	@Override
	public Mono<T> findByIdWithRouting(ID id, String routing) {

		var stringId = operations.getElasticsearchConverter().convertId(id);
		return operations.withRouting(RoutingResolver.just(routing)).get(stringId, clazz);
	}
}
