/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.cities;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface CityRepository extends ReactiveElasticsearchRepository<City, String> {
}
