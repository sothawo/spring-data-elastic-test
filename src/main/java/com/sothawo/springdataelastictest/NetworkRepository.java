/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface NetworkRepository extends ElasticsearchRepository<Network, String> {
	List<Network> findByCidr(String ip);
}
