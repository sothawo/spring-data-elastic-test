package com.sothawo.springdataelastictest.population

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.reactive.asFlow
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Service
class PopulationService(private val personRepository: PopulationPersonRepository, private val houseRepository: PopulationHouseRepository) {

	fun deleteAll(): Mono<Void> =
		personRepository.deleteAll().then(houseRepository.deleteAll())

	fun createHouses(count: Int): Flux<String> {

		val houses = Flux.range(1, count)
			.map { House.create("house-$it") }

		return houseRepository.saveAll(houses)
			.mapNotNull { it.id }

	}

	fun createPersons(count: Int): Flux<String> {

		return houseRepository.findAll()
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
	}

	fun createPersonsInHouses(numPersons: Int, numHouses: Int): Mono<Void> {
		return createHouses(numHouses)
			.then(createPersons(numPersons).then())
	}

	fun getByPersonsName(name: String): Flux<HouseDTO> {
		return personRepository.searchByLastNameOrFirstName(name, name)
			.mapNotNull { it.house }
			.collectList()
			.flatMapMany { houseIds ->
				if (houseIds.isEmpty()) {
					Flux.empty()
				} else {
					houseRepository.findAllById(houseIds)
						.flatMap { house ->
							personRepository.searchByHouse(house.id!!)
								.collectList()
								.map { persons ->
									HouseDTO(
										house.id!!, house.zip, house.city, house.street, house.streetNumber,
										persons.map { HouseDTO.PersonDTO(it.id!!, it.lastName, it.firstName) }
									)
								}
						}
				}
			}
	}

	suspend fun getByPersonsNameCR(name: String): Flow<HouseDTO> {
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
