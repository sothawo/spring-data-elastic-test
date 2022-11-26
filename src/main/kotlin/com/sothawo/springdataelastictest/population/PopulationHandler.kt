package com.sothawo.springdataelastictest.population

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Component
class PopulationHandler(private val populationService: PopulationService) {

	suspend fun deleteAll(request: ServerRequest): ServerResponse {
		populationService.deleteAll()
		return ok().buildAndAwait()
	}

	suspend fun createPersonsInHouses(request: ServerRequest): ServerResponse {

		val numHouses = request.pathVariable("numHouses").toInt()
		val numPersons = request.pathVariable("numPersons").toInt()
		populationService.createPersonsInHouses(numPersons, numHouses)
		return ok().buildAndAwait()
	}
	suspend fun personsByName(request: ServerRequest): ServerResponse {
		val name = request.pathVariable("name")
		return ok().bodyAndAwait(populationService.getByPersonsName(name))
	}
}
