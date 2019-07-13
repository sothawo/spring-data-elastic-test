package com.sothawo.springdataelastictest

import org.springframework.data.elasticsearch.annotations.Query
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface PersonRepository : ElasticsearchRepository<Person, Long> {
    fun findByLastName(lastName: String): List<Person>

    @Query(value = "{\"fuzzy\":{\"last-name\":\"?0\"}}")
    fun findByLastNameFuzzy(lastName: String): List<Person>

    fun findAllByOrderByFirstName(): List<Person>
}
