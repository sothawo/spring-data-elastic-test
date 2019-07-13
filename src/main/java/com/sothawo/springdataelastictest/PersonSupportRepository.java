/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * needed to create the index and put the mapping in the beginning.
 *
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface PersonSupportRepository extends ElasticsearchRepository<Person, Long> {
}
