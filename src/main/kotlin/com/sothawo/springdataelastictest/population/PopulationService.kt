package com.sothawo.springdataelastictest.population

import kotlinx.coroutines.flow.*
import org.springframework.stereotype.Service

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Service
class PopulationService(private val personRepository: PopulationPersonRepository, private val houseRepository: PopulationHouseRepository) {

		suspend fun deleteAll() {
				personRepository.deleteAll()
				houseRepository.deleteAll()
		}

		suspend fun createHouses(count: Int): Flow<String> {
				val houses = IntRange(1, count).map { House.create("house-$it") }
				return houseRepository.saveAll(houses).mapNotNull { it.id }
		}

		suspend fun createPersons(count: Int): Flow<String> {

				val houseIds = mutableListOf<String>()
				houseRepository.findAll().mapNotNull { it.id }.toCollection(houseIds)

				return IntRange(1, count)
						.map { Person.create("person-$it") }
						.map { person ->
								person.house = houseIds.random()
								personRepository.save(person).id
						}.asFlow().mapNotNull { it }
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

		suspend fun personsByLastNameOrFirstName(name: String) = personRepository.searchByLastNameOrFirstName(name, name)
		suspend fun housesById(houseIds: Collection<String>) = houseRepository.findAllById(houseIds)
		suspend fun personsByHouse(houseId: String) = personRepository.searchByHouse(houseId)
}
