/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.person;

import reactor.core.publisher.Mono;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface RoutingRepository<T, ID> {
	Mono<T> findByIdWithRouting(ID id, String routing);
}
