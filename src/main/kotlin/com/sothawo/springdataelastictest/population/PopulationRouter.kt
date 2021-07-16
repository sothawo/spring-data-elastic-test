package com.sothawo.springdataelastictest.population

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.DELETE
import org.springframework.web.reactive.function.server.RequestPredicates.PUT
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.RouterFunctions.route

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration("PopulationRouterConfiguration")
class PopulationRouter {

    @Bean
    fun populationRouter(handler: PopulationHandler) =
        nest(
            path("/population"),
            route(DELETE(""), handler::deleteAll)
                .andRoute(PUT("/{numHouses}/{numPersons}"), handler::createPersonsInHouses)
        )
}
