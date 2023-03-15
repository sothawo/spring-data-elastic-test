/*
 * (c) Copyright 2023 sothawo
 */
package com.sothawo.springdataelastictest.foo;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Component
public class FooHandler {
	private FooService service;

	public FooHandler(FooService service) {
		this.service = service;
	}

	public Mono<ServerResponse> test(ServerRequest serverRequest) {
		return ok().body(service.test3(), Foo.class);
	}
}
