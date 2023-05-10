package com.sothawo.springdataelastictest.so

import org.springframework.data.elasticsearch.repository.CoroutineElasticsearchRepository

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface FooRepository : CoroutineElasticsearchRepository<Foo, String>
