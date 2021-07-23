package com.sothawo.springdataelastictest.population

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration("PopulationRouterCRConfiguration")
class PopulationRouterCR {

	@Bean
	fun populationRouterCR(handler: PopulationHandler) = coRouter {
		path("/populationCR").nest {
			GET("/{name}", handler::personsByNameCR)
		}
	}
}
