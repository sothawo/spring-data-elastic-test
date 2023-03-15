/*
 * (c) Copyright 2023 sothawo
 */
package com.sothawo.springdataelastictest.foo;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Service
public class FooService {
	private final FooRepository repository;

	public FooService(FooRepository repository) {
		this.repository = repository;
	}

public Flux<Foo> test1() {
	System.out.println("test");

	return Flux.range(1, 1_000_000)
		.map(Foo::of)
		.bufferTimeout(1000, Duration.ofSeconds(1))
		.flatMapSequential(repository::saveAll);
}

public Flux<Foo> test2() {
	System.out.println("test");

	Flux.range(1, 1_000_000)
		.map(Foo::of)
		.bufferTimeout(1000, Duration.ofSeconds(1))
		.subscribe(new Subscriber<>() {
			private Instant start;
			private Subscription subscription;
			private int count = 0;

			@Override
			public void onSubscribe(Subscription subscription) {
				System.out.println("onSubscribe");
				this.subscription = subscription;
				subscription.request(1);
				this.start = Instant.now();
			}

			@Override
			public void onNext(List<Foo> foos) {
				System.out.println("onNext " + ++count);
				repository.saveAll(foos)
					.doOnComplete(() -> {
						subscription.request(1);
					})
					.subscribe();
			}

			@Override
			public void onError(Throwable throwable) {
				throwable.printStackTrace();
				subscription.cancel();
				throw new RuntimeException("oops, something went wrong", throwable);
			}

			@Override
			public void onComplete() {
				var duration = Duration.between(Instant.now(), start);
				System.out.println("complete after " + duration.toString());
			}
		});
	return Flux.empty();
}

public Flux<Foo> test3() {
	System.out.println("test");

	Sinks.Many<Foo> sink = Sinks.many().unicast().onBackpressureBuffer();

	Flux.range(1, 10_000)
		.map(Foo::of)
		.bufferTimeout(1000, Duration.ofSeconds(1))
		.subscribe(new Subscriber<>() {
			private Instant start;
			private Subscription subscription;
			volatile private int count = 0;
			volatile boolean upstreamComplete = false;

			@Override
			public void onSubscribe(Subscription subscription) {
				System.out.println("onSubscribe");
				this.subscription = subscription;
				subscription.request(1);
				this.start = Instant.now();
			}

			@Override
			public void onNext(List<Foo> foos) {
				System.out.println("onNext " + ++count);
				repository.saveAll(foos)
					.map(sink::tryEmitNext)
					.doOnComplete(() -> {
						System.out.println("save complete " + count);
						if (!upstreamComplete) {
							subscription.request(1);
						} else {
							var duration = Duration.between(Instant.now(), start);
							System.out.println("complete after " + duration.toString());
							sink.tryEmitComplete();
						}
					})
					.subscribe();
			}

			@Override
			public void onError(Throwable throwable) {
				throwable.printStackTrace();
				subscription.cancel();
				sink.tryEmitError(throwable);
			}

			@Override
			public void onComplete() {
				upstreamComplete = true;
			}
		});

	return sink.asFlux();
}
}
