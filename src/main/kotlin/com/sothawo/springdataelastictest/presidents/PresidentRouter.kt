package com.sothawo.springdataelastictest.presidents

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.RouterFunctions.route

@Configuration("PresidentRouterConfiguration")
class PresidentRouter {

    @Bean
    fun presidentRouter(handler: PresidentHandler) =
        nest(
            path("/presidents"),
            route(GET("/load"), handler::load)
                .andRoute(GET("/name/{name}"), handler::searchByName)
                .andRoute(GET("/term/{year}"), handler::searchByTerm)
                .andRoute(GET("/{id}"), handler::byId)
        )
}
