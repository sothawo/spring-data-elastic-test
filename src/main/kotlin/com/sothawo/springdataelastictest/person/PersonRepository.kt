package com.sothawo.springdataelastictest.person

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository
import reactor.core.publisher.Flux

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface PersonRepository : ReactiveElasticsearchRepository<Person, Long>
