package com.sothawo.springdataelastictest.person

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration("PersonRouterConfiguration")
class PersonRouter {

	@Bean
	fun personRouter(handler: PersonHandler) = coRouter {
		"/persons".nest {
			GET("/create/{count}", handler::create)
			GET("/all", handler::all)
			GET("/all-with-age", handler::allWithAge)
		}
	}
}
