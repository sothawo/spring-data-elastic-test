/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.person;

import com.sothawo.springdataelastictest.RoutingAwareReactiveElasticsearchRepository;
import org.springframework.data.elasticsearch.core.SearchHit;
import reactor.core.publisher.Flux;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface PersonRepository extends RoutingAwareReactiveElasticsearchRepository<Person, Long> {
		Flux<SearchHit<Person>> searchByLastName(String lastName);
}
