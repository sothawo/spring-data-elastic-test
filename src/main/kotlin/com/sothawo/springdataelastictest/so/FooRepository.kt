package com.sothawo.springdataelastictest.so

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface FooRepository : ElasticsearchRepository<Foo, String>
