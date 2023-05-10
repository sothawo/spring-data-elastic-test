package com.sothawo.springdataelastictest.population

import org.springframework.data.elasticsearch.repository.CoroutineElasticsearchRepository

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface PopulationHouseRepository : CoroutineElasticsearchRepository<House, String> {
}
