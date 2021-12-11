/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.person;

import com.sothawo.springdataelastictest.RoutingAwareReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface PersonRepository extends RoutingAwareReactiveElasticsearchRepository<Person, Long> {
	Flux<Person> findAllByLastName(String name);
}
