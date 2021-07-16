package com.sothawo.springdataelastictest.population

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
}
