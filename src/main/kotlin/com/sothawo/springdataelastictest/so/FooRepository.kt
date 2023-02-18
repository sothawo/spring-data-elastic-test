package com.sothawo.springdataelastictest.so

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface FooRepository : ReactiveElasticsearchRepository<Foo, String>
