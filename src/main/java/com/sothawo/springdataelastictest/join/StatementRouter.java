/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.join;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Component
public class StatementRouter {

    @Bean
    public RouterFunction<ServerResponse> route(StatementHandler statementHandler) {

        return RouterFunctions.route()
            .path("/statements", builder -> builder
                .GET("/{id}/{routing}", statementHandler::getByIdAndRouting)
                .GET("/{id}", statementHandler::getByIdAndRouting)
                .DELETE("/{id}/{routing}", statementHandler::deleteByIdAndRouting)
                .DELETE("/{id}/", statementHandler::deleteByIdAndRouting)
                .DELETE("/clear", statementHandler::clear)
                .DELETE(statementHandler::delete)
                .POST(statementHandler::save)
                .GET(statementHandler::all))
            .build();
    }
}
