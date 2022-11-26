package com.sothawo.springdataelastictest.presidents

import com.sothawo.springdataelastictest.ResourceNotFoundException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Service
class PresidentService(
	private val repository: PresidentRepository,
	private val operations: ReactiveElasticsearchOperations
) {
	suspend fun load() = repository.deleteAll()
		.thenMany(
			repository.saveAll(
				listOf(
					President.of("Dwight D Eisenhower", 1953, 1961),
					President.of("Lyndon B Johnson", 1963, 1969),
					President.of("Richard Nixon", 1969, 1974),
					President.of("Gerald Ford", 1974, 1977),
					President.of("Jimmy Carter", 1977, 1981),
					President.of("Ronald Reagen", 1981, 1989),
					President.of("George Bush", 1989, 1993),
					President.of("Bill Clinton", 1993, 2001),
					President.of("George W Bush", 2001, 2009),
					President.of("Barack Obama", 2009, 2017),
					President.of("Donald Trump", 2017, 2021),
					President.of("Joe Biden", 2021, 2025)
				)
			)
		).asFlow()

	suspend fun byId(id: String) = repository.findById(id)
		.switchIfEmpty(Mono.error(ResourceNotFoundException())).awaitSingle()

	suspend fun searchByTerm(year: Int) = repository.searchByTerm(year).asFlow()

	suspend fun searchByName(name: String, requestCache: Boolean?): Flow<SearchHit<President>> {
		val query = CriteriaQuery(Criteria("name").`is`(name))
		requestCache?.let { query.requestCache = it }

		return operations.count(query, President::class.java)
			.flatMapMany<SearchHit<President>?> { count: Long? ->
				LOGGER.info("#presidents: {}", count)
				operations.search(query, President::class.java)
			}.asFlow()
	}

	companion object {
		private val LOGGER = LoggerFactory.getLogger(PresidentHandler::class.java)
	}

}
