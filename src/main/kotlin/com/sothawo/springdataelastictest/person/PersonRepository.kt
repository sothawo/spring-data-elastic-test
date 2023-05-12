package com.sothawo.springdataelastictest.person

import kotlinx.coroutines.flow.Flow
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.data.elasticsearch.repository.CoroutineElasticsearchRepository
import reactor.core.publisher.Flux

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface PersonRepository : CoroutineElasticsearchRepository<Person, Long> {

		// one function to return a flow
		suspend fun findByLastName(lastName: String): Flow<SearchHit<Person>>
}
