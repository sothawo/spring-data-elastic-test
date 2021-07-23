package com.sothawo.springdataelastictest.population

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository
import reactor.core.publisher.Flux

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface PopulationPersonRepository : ReactiveElasticsearchRepository<Person, String> {

	fun searchByLastNameOrFirstName(lastName: String, firstame: String): Flux<Person>
	fun searchByHouse(id: String): Flux<Person>
}
