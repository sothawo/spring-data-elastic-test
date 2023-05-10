package com.sothawo.springdataelastictest.population

import kotlinx.coroutines.flow.Flow
import org.springframework.data.elasticsearch.repository.CoroutineElasticsearchRepository

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface PopulationPersonRepository : CoroutineElasticsearchRepository<Person, String> {

	fun searchByLastNameOrFirstName(lastName: String, firstName: String): Flow<Person>
	fun searchByHouse(id: String): Flow<Person>
}
