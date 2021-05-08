package com.sothawo.springdataelastictest.presidents

import org.slf4j.LoggerFactory
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Service
class PresidentService(
    private val repository: PresidentRepository,
    private val operations: ElasticsearchOperations,
) {
    fun load(): Iterable<President> {
        repository.deleteAll()
        return repository.saveAll(
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
    }

    fun byId(id: String) = repository.findByIdOrNull(id)

    fun searchByTerm(year: Int) = repository.searchByTerm(year)

    fun searchByname(name: String, requestCache: Boolean?) : SearchHits<President> {
        val query = CriteriaQuery(Criteria("name").`is`(name))
        requestCache?.let {
            query.requestCache = it
        }

        val count = operations.count(query, President::class.java)
        LOGGER.info("#presidents: {}", count)
        return operations.search(query, President::class.java)

    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(PresidentService::class.java)
    }
}
