package com.sothawo.springdataelastictest;

import reactor.core.publisher.Flux;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface ReactivePersonRepository extends ReactiveElasticsearchRepository<Person, Long> {
	Flux<Person> findByLastName(final String lastName);
}
