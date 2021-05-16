package com.sothawo.springdataelastictest.person

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface PersonRepository : ElasticsearchRepository<Person, Long>
