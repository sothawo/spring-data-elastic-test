package com.sothawo.springdataelastictest.presidents

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration("PresidentRouterConfiguration")
class PresidentRouter {

	@Bean
	fun presidentRouter(handler: PresidentHandler) = coRouter {
		"/presidents".nest {
			GET("/load", handler::load)
			GET("/name/{name}", handler::searchByName)
			GET("/term/{year}", handler::searchByTerm)
			GET("/{id}", handler::byId)
		}
	}
}
