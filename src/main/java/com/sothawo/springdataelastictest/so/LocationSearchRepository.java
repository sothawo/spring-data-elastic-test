/*
 * (c) Copyright 2022 sothawo
 */
package com.sothawo.springdataelastictest.so;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface LocationSearchRepository extends ElasticsearchRepository<LocationSearch, String> {
}
