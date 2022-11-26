package com.sothawo.springdataelastictest.population

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Service
class PopulationService(private val personRepository: PopulationPersonRepository, private val houseRepository: PopulationHouseRepository) {

	suspend fun deleteAll() {
		personRepository.deleteAll().awaitSingleOrNull()
		houseRepository.deleteAll().awaitSingleOrNull()
	}

	suspend fun createHouses(count: Int): Flow<String> {

		val houses = Flux.range(1, count)
			.map { House.create("house-$it") }

		val createdIds: Flux<String> = houseRepository.saveAll(houses)
			.mapNotNull { it.id }
		return createdIds.asFlow()
	}

	suspend fun createPersons(count: Int): Flow<String> {

		val createdIds: Flux<String> = houseRepository.findAll()
			.mapNotNull { it.id }.collectList()
			.flatMapMany { houseIds ->
				val persons = Flux.range(1, count)
					.map { Person.create("person-$it") }
					.map {
						it.house = houseIds.random()
						it
					}

				personRepository.saveAll(persons)
					.mapNotNull { it.id }
			}

		return createdIds.asFlow()
	}


	suspend fun createPersonsInHouses(numPersons: Int, numHouses: Int) {
		createHouses(numHouses)
		createPersons(numPersons)
	}

	suspend fun getByPersonsName(name: String): Flow<HouseDTO> {
		val houseIds = personsByLastNameOrFirstName(name).mapNotNull { it.house }.toCollection(mutableListOf())
		return housesById(houseIds).map { house ->
			HouseDTO(
				house.id!!, house.zip, house.city, house.street, house.streetNumber,
				personsByHouse(house.id!!)
					.map { HouseDTO.PersonDTO(it.id!!, it.lastName, it.firstName) }.toCollection(mutableListOf())
			)
		}
	}

	suspend fun personsByLastNameOrFirstName(name: String) = personRepository.searchByLastNameOrFirstName(name, name).asFlow()
	suspend fun housesById(houseIds: Collection<String>) = houseRepository.findAllById(houseIds).asFlow()
	suspend fun personsByHouse(houseId: String) = personRepository.searchByHouse(houseId).asFlow()
}
