/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ReactiveIndexOperations;
import org.springframework.data.elasticsearch.core.routing.RoutingResolver;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.SimpleReactiveElasticsearchRepository;
import reactor.core.publisher.Mono;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class RoutingAwareReactiveElasticsearchRepositoryImpl<T, ID> extends SimpleReactiveElasticsearchRepository<T, ID> implements RoutingAwareReactiveElasticsearchRepository<T, ID> {

	private final ElasticsearchEntityInformation<T, ID> entityInformation;
	private final ReactiveElasticsearchOperations operations;
	private final ReactiveIndexOperations indexOperations;

	public RoutingAwareReactiveElasticsearchRepositoryImpl(ElasticsearchEntityInformation<T, ID> entityInformation, ReactiveElasticsearchOperations operations) {
		super(entityInformation, operations);
		this.entityInformation = entityInformation;
		this.operations = operations;
		this.indexOperations = operations.indexOps(entityInformation.getJavaType());
	}

	@Override
	public Mono<T> findByIdWithRouting(ID id, String routing) {
		var stringId = operations.getElasticsearchConverter().convertId(id);
		return operations.withRouting(RoutingResolver.just(routing)).get(stringId, entityInformation.getJavaType());
	}
}
