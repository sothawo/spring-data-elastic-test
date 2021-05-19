package com.sothawo.springdataelastictest.person

import org.elasticsearch.index.query.QueryBuilders.matchAllQuery
import org.elasticsearch.script.Script
import org.elasticsearch.script.ScriptType
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.data.elasticsearch.core.query.Query
import org.springframework.data.elasticsearch.core.query.ScriptField
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Collections
import java.util.stream.Stream
import java.util.stream.StreamSupport
import kotlin.math.min

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/persons")
class PersonController(
    private val repository: PersonRepository,
    private val operations: ElasticsearchOperations,
) {

    @GetMapping("/create/{count}")
    fun create(@PathVariable("count") count: Long) {

        repository.deleteAll()
        var fromId = 1L

        while (fromId < count) {
            val toId = min(fromId + 1000, count)
            val persons = (fromId..toId).map(Person.Companion::create)
            repository.saveAll(persons)
            fromId += 1000L
        }
    }

    @GetMapping("/all")
    fun all(): Stream<Person?>? = StreamSupport.stream(repository.findAll().spliterator(), false)

    @GetMapping("/all-with-age")
    fun allWithAge(): Stream<Person> {

        val query = Query.findAll().apply {
            addFields("age")
            addSourceFilter(FetchSourceFilterBuilder().withIncludes("*").build())
        }
        val searchHits = operations.search(query, Person::class.java)
        return searchHits.get().map { it.content }
    }
}
