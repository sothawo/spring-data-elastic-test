/*
 * (c) Copyright 2023 sothawo
 */
package com.sothawo.springdataelastictest.foo;

import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
				var foo = new Foo();

				foo.setId("1");
				foo.setText("immediate - default");
				return repository.save(foo)
								.flatMap(f -> {
														foo.setId("2");
														foo.setText("none");
														return repository.save(foo, RefreshPolicy.NONE);
												}
								)
								.flatMap(f -> {
														foo.setId("3");
														foo.setText("wait_until");
														return repository.save(foo, RefreshPolicy.WAIT_UNTIL);
												}
								)
								.flatMap(f -> {
														foo.setId("4");
														foo.setText("immediate - default");
														return repository.save(foo);
												}
								)
								.then();
		}
}
