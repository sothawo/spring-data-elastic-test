package com.sothawo.springdataelastictest.population

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Component
class PopulationHandler(private val populationService: PopulationService) {

	fun deleteAll(request: ServerRequest): Mono<ServerResponse> =
		populationService.deleteAll()
			.then(ServerResponse.ok().build())

	fun createPersonsInHouses(request: ServerRequest): Mono<ServerResponse> {

		val numHouses = request.pathVariable("numHouses").toInt()
		val numPersons = request.pathVariable("numPersons").toInt()
		return populationService.createPersonsInHouses(numPersons, numHouses)
			.then(ServerResponse.ok().build())
	}

	fun personsByName(request: ServerRequest): Mono<ServerResponse> {
		val name = request.pathVariable("name")
		return ServerResponse.ok().body (populationService.getByPersonsName(name), Flux::class.java)
	}
}
