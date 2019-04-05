package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface PersonRepository extends ElasticsearchRepository<Person, Long> {
    Optional<Person> findByLastName(final String lastName);
}
