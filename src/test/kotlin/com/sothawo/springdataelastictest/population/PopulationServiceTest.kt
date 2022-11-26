package com.sothawo.springdataelastictest.population

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class)
class PopulationServiceTest {

	private val houseRepository = mock<PopulationHouseRepository>()
	private val personRepository = mock<PopulationPersonRepository>()

	private val populationService = PopulationService(personRepository, houseRepository)

	@Test
	fun `should delete from both repositories`() = runTest {

		whenever(personRepository.deleteAll()).thenReturn(Mono.empty())
		whenever(houseRepository.deleteAll()).thenReturn(Mono.empty())

		populationService.deleteAll()

		verify(personRepository).deleteAll()
		verify(houseRepository).deleteAll()
	}

	@Test
	fun `should return ids of created houses`() = runTest {

		whenever(houseRepository.saveAll(any<Flux<House>>()))
			.then { it.getArgument<Flux<House>?>(0) }

		val createdCount = populationService.createHouses(100).count()

		assertThat(createdCount).isEqualTo(100)
	}

	@Test
	fun `should return ids of created persons that have houses set`() = runTest {

		val houseIds = IntRange(1, 42).map { "house-$it" }.toList()

		whenever(houseRepository.findAll())
			.thenReturn(Flux.fromIterable(houseIds)
				.map { House.create(it) })

		whenever(personRepository.saveAll(any<Flux<Person>>()))
			.then {
				it.getArgument<Flux<Person>?>(0)
					.doOnNext { person ->
						assertThat(houseIds).contains(person.house)
					}
			}

		val createdCount = populationService.createPersons(100).count()

		assertThat(createdCount).isEqualTo(100)
	}

	@Test
	fun `should return HouseDTO for searched person`() = runTest {
		val person1 = Person("person-1", "Smith", "John", "house-42")
		val person2 = Person("person-2", "Miller", "Jane", "house-42")
		whenever(personRepository.searchByLastNameOrFirstName("Smith", "Smith")).thenReturn(Flux.just(person1))
		whenever(personRepository.searchByHouse("house-42")).thenReturn(Flux.just(person1, person2))

		val house = House("house-42", "12345", "SomeCity", "SomeStreet", "42")
		whenever(houseRepository.findAllById(any<Iterable<String>>())).thenReturn(Flux.just(house))

		val expected = HouseDTO(
			"house-42", "12345", "SomeCity", "SomeStreet", "42",
			listOf(
				HouseDTO.PersonDTO("person-1", "Smith", "John"),
				HouseDTO.PersonDTO("person-2", "Miller", "Jane"),
			)
		)

		val houseDTOS = populationService.getByPersonsName("Smith").toList()

		assertThat(houseDTOS).containsExactly(expected)
	}
}
