package com.sothawo.springdataelastictest.presidents

import com.sothawo.springdataelastictest.ResourceNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.lang.Nullable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("presidents")
class PresidentController(private val repository: PresidentRepository, private val operations: ElasticsearchOperations) {

    @GetMapping("/load")
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

    @GetMapping("/term/{year}")
    fun searchByTerm(@PathVariable year: Int): SearchHits<President> {
        return repository.searchByTerm(year)
    }

    @GetMapping("/name/{name}")
    fun searchByName(@PathVariable name: String?, @RequestParam(required = false) requestCache: Boolean?): SearchHits<President> {
        val query = CriteriaQuery(Criteria("name").`is`(name!!))
        if (requestCache != null) {
            query.requestCache = requestCache
        }
        val count = operations.count(query, President::class.java)
        LOGGER.info("#presidents: {}", count)
        return operations.search(query, President::class.java)
    }

    @GetMapping("/{id}")
    @Nullable
    fun byId(@PathVariable id: String?): President {
        return operations[id!!, President::class.java] ?: throw ResourceNotFoundException()
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(PresidentController::class.java)
    }
}
