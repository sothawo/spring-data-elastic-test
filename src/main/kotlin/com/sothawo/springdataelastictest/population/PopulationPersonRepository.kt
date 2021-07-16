package com.sothawo.springdataelastictest.population

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface PopulationPersonRepository : ReactiveElasticsearchRepository<Person, Long> {
}
