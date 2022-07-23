/*
 * (c) Copyright 2022 sothawo
 */
package com.sothawo.springdataelastictest.compositeid;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface CompositeRepository extends ElasticsearchRepository<CompositeEntity,
	String> {
}
