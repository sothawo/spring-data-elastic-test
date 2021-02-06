/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.customid;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface CustomIdRepository extends ElasticsearchRepository<CustomId, String> {
    SearchHits<CustomId> searchBy();
}
