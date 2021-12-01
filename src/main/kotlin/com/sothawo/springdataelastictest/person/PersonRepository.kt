package com.sothawo.springdataelastictest.person

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface PersonRepository : ReactiveElasticsearchRepository<Person, Long>
