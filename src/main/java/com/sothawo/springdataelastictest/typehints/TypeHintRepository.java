/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.typehints;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface TypeHintRepository extends ElasticsearchRepository<BaseClass, String> {
	SearchHits<? extends BaseClass> searchAllBy();
}
