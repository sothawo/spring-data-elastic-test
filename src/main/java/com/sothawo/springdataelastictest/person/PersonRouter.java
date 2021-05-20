/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.person;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration("PersonRouterConfiguration")
public class PersonRouter {

    @Bean
    public RouterFunction<ServerResponse> personRouter(PersonHandler handler) {
        return nest(path("/persons"),
            route(GET("/create/{count}"), handler::create)
                .andRoute(GET("/all"), handler::all)
                .andRoute(GET("/all-with-age"), handler::allWithAge)
        );
    }
}
