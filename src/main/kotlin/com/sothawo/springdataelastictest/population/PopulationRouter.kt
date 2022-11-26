package com.sothawo.springdataelastictest.population

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration("PopulationRouterConfiguration")
class PopulationRouter {

	@Bean
	fun populationRouterCR(handler: PopulationHandler) = coRouter {
		"/population".nest {
			DELETE("", handler::deleteAll)
			PUT("/{numHouses}/{numPersons}", handler::createPersonsInHouses)
			GET("/{name}", handler::personsByName)
		}
	}
}
