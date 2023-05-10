package com.sothawo.springdataelastictest.presidents

import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.data.elasticsearch.repository.CoroutineElasticsearchRepository
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository
import reactor.core.publisher.Flux

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface PresidentRepository : CoroutineElasticsearchRepository<President, String> {
    fun searchByTerm(year: Int): Flux<SearchHit<President>>
}
