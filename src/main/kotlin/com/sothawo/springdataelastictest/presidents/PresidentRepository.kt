package com.sothawo.springdataelastictest.presidents

import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface PresidentRepository : ElasticsearchRepository<President, String> {
    fun searchByTerm(year: Int): SearchHits<President>
}
