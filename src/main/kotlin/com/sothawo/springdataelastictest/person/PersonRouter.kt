package com.sothawo.springdataelastictest.person

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration("PersonRouterConfiguration")
class PersonRouter {

    @Bean
    fun personRouter(handler: PersonHandler) = router {
        "/persons".nest {
            GET("/create/{count}").invoke(handler::create)
            GET("/all").invoke(handler::all)
            GET("/all-with-age").invoke(handler::allWithAge)
        }
    }
}
