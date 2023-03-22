/*
 * (c) Copyright 2023 sothawo
 */
package com.sothawo.springdataelastictest.foo;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Service
public class FooService {
	private final FooRepository repository;

	public FooService(FooRepository repository) {
		this.repository = repository;
	}

public Mono<Void> test() {
	var start = Instant.now();

	var fooFlux =  Flux.range(1, 1_000_000)
		.map(Foo::of);

	return repository.saveAll(fooFlux)
		.doOnComplete(() -> {
			System.out.println(Duration.between(start, Instant.now()));
		})
		.then();
}
}
