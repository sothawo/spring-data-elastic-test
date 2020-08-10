/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.italy;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface ItalyRepository extends ElasticsearchRepository<Italy.City, String> {
}
