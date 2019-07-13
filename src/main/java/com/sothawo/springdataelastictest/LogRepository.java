/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface LogRepository extends ElasticsearchRepository<LogEntity, String> {
}
