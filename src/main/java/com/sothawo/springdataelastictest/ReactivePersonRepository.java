package com.sothawo.springdataelastictest;

import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface ReactivePersonRepository extends ReactiveElasticsearchRepository<Person, Long> {
	Optional<Person> findByLastName(final String lastName);
}
