/*
 * (c) Copyright 2023 sothawo
 */
package com.sothawo.springdataelastictest.foo;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface FooRepository extends ReactiveElasticsearchRepository<Foo, String> {
}
