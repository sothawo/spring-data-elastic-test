/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.person;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface PersonRepository extends ReactiveElasticsearchRepository<Person, Long> {
}
