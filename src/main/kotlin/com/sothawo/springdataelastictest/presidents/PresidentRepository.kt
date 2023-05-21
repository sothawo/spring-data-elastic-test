package com.sothawo.springdataelastictest.presidents

import kotlinx.coroutines.flow.Flow
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.data.elasticsearch.repository.CoroutineElasticsearchRepository

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface PresidentRepository : CoroutineElasticsearchRepository<President, String> {
    suspend fun searchByTerm(year: Int): Flow<SearchHit<President>>
}
