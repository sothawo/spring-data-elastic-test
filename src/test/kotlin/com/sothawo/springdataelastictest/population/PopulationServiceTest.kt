package com.sothawo.springdataelastictest.population

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@ExtendWith(MockitoExtension::class)
class PopulationServiceTest {

    private val houseRepository = mock<PopulationHouseRepository>()
    private val personRepository = mock<PopulationPersonRepository>()

    private val populationService = PopulationService(personRepository, houseRepository)

    @Test
    fun `should delete from both repositories`() {

        whenever(personRepository.deleteAll()).thenReturn(Mono.empty())
        whenever(houseRepository.deleteAll()).thenReturn(Mono.empty())

        populationService.deleteAll()
            .`as`(StepVerifier::create)
            .verifyComplete()

        verify(personRepository).deleteAll()
        verify(houseRepository).deleteAll()
    }

    @Test
    fun `should return ids of created houses`() {

        whenever(houseRepository.saveAll(any<Flux<House>>()))
            .then { it.getArgument<Flux<House>?>(0) }

        populationService.createHouses(100)
            .collectList()
            .`as`(StepVerifier::create)
            .consumeNextWith {
                assertThat(it).hasSize(100)
            }
            .verifyComplete()
    }

    @Test
    fun `should return ids of created persons that have houses set`() {

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

        populationService.createPersons(100)
            .collectList()
            .`as`(StepVerifier::create)
            .consumeNextWith {
                assertThat(it).hasSize(100)
            }
            .verifyComplete()
    }
}
