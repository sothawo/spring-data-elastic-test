package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

public interface PersonRepository extends ElasticsearchRepository<Person, Long> {
}
