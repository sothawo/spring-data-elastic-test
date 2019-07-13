package com.sothawo.springdataelastictest;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

public interface PersonRepository extends ElasticsearchRepository<Person, Long> {
	List<Person> findByLastName(final String lastName);

	@Query(value = "{\"fuzzy\":{\"last-name\":\"?0\"}}")
	List<Person> findByLastNameFuzzy(final String lastName);
}
