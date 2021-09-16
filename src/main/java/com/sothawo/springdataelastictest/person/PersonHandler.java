/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.person;

import org.springframework.data.elasticsearch.core.AggregationsContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@SuppressWarnings("unused")
@Component
public class PersonHandler {

	private final PersonService service;

	public PersonHandler(PersonService service) {
		this.service = service;
	}

	public Mono<ServerResponse> create(ServerRequest request) {
		var count = Integer.parseInt(request.pathVariable("count"));
		return service.create(count).then(ok().build());
	}

	public Mono<ServerResponse> all(ServerRequest request) {
		return ok().body(service.all(), Person.class);
	}

	public Mono<ServerResponse> allWithAge(ServerRequest request) {
		return ok().body(service.allWithAge(), Person.class);
	}

	public Mono<ServerResponse> lastNameCounts(ServerRequest request) {
		return ok().body(service.lastNameCounts(), AggregationsContainer.class);
	}

	public Mono<ServerResponse> byIdWithRouting(ServerRequest request) {
		var id = request.pathVariable("id");
		var routing = request.queryParam("routing").orElseThrow();
		return ok().body(service.byIdWithrouting(id, routing), Person.class);
	}
}
