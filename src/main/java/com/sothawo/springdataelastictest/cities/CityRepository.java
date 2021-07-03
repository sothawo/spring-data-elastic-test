/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.cities;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface CityRepository extends ElasticsearchRepository<City, String> {
}