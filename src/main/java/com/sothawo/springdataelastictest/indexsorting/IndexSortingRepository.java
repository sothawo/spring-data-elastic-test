/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.indexsorting;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface IndexSortingRepository  extends ElasticsearchRepository<IndexSortingEntity, String> {
}
