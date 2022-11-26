package com.sothawo.springdataelastictest.person

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder
import org.springframework.data.elasticsearch.core.query.Query
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Service
class PersonService(
	private val repository: PersonRepository,
	private val operations: ReactiveElasticsearchOperations,
) {
	suspend fun create(count: Int) {
		repository.deleteAll()
			.then(Flux.range(1, count)
				.window(1000)
				.map { ids -> ids.map { id -> Person.create(id.toLong()) } }
				.flatMap(repository::saveAll)
				.then())
	}

	suspend fun all() = repository.findAll().asFlow()

	suspend fun allWithAge(): Flow<Person> {

		val query = Query.findAll().apply {
			addFields("age")
			addSourceFilter(FetchSourceFilterBuilder().withIncludes("*").build())
		}
		return operations.search(query, Person::class.java).map { it.content }.asFlow()
	}
}
