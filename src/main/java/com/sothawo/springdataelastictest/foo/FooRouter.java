/*
 * (c) Copyright 2023 sothawo
 */
package com.sothawo.springdataelastictest.foo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration("FooRouterConfiguration")
public class FooRouter {

	@Bean
	public RouterFunction<ServerResponse> fooRouter(FooHandler handler) {
		return nest(path("/foo"),
			route(GET("/test"), handler::test)
			);
	}
}
