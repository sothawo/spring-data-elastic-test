package com.sothawo.springdataelastictest;

import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;

public interface ReactivePersonRepository extends ReactiveElasticsearchRepository<Person, Long> {
	Flux<Person> findByLastName(final String lastName);
}
