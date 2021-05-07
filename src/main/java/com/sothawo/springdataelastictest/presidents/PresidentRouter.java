package com.sothawo.springdataelastictest.presidents;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

@Configuration("PresidentRouterConfiguration")
class PresidentRouter {

    @Bean
    public RouterFunction<ServerResponse> presidentRouter(PresidentHandler handler) {
        return nest(path("/presidents"),
            route(GET("/load"), handler::load)
                .andRoute(GET("/name/{name}"), handler::searchByName)
                .andRoute(GET("/term/{year}"), handler::searchByTerm)
                .andRoute(GET("/{id}"), handler::byId));
    }
}
