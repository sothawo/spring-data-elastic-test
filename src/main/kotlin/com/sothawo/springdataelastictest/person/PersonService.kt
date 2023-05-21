package com.sothawo.springdataelastictest.person

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.reactive.asFlow
import org.slf4j.LoggerFactory
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder
import org.springframework.data.elasticsearch.core.query.Query
import org.springframework.data.elasticsearch.core.search
import org.springframework.stereotype.Service
import kotlin.math.min

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Service
class PersonService(
    private val repository: PersonRepository,
    private val operations: ReactiveElasticsearchOperations,
) {
    suspend fun create(count: Long) {
        LOGGER.info("creating {} persons", count)
        repository.deleteAll()
        var fromId = 1L

        while (fromId < count) {
            val toId = min(fromId + 1000, count)
			LOGGER.info("creating persons from {} to {}", fromId, toId)
            val persons = (fromId..toId).map(Person.Companion::create)
			// note: saveAll is no suspend function!
            repository.saveAll(persons).collect()
            fromId += 1000L
        }
        LOGGER.info("created {} persons", count)
    }

    suspend fun all() = repository.findAll()

    suspend fun allWithAge(): Flow<Person> {

        val query = Query.findAll().apply {
            addFields("age")
            addSourceFilter(FetchSourceFilterBuilder().withIncludes("*").build())
        }
        return operations.search<Person>(query).map { it.content }.asFlow()
    }

    suspend fun byLastName(lastName: String): Flow<SearchHit<Person>> {
        return repository.findByLastName(lastName)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(PersonService::class.java)
    }
}
