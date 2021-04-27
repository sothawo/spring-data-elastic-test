/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.so;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface TableRepository extends ElasticsearchRepository<Table, String> {
}
