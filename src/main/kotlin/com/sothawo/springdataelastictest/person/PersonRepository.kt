package com.sothawo.springdataelastictest.person

import org.springframework.data.elasticsearch.repository.CoroutineElasticsearchRepository

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface PersonRepository : CoroutineElasticsearchRepository<Person, Long>
