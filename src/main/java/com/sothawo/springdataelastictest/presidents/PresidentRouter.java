package com.sothawo.springdataelastictest.presidents;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration("PresidentRouterConfiguration")
class PresidentRouter {

    @Bean
    public RouterFunction<ServerResponse> presidentRouter(PresidentHandler handler) {
        return RouterFunctions
                .route(GET("/preesidents/load"), handler::load);
    }
}
