/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.products;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface ProductRepository extends ElasticsearchRepository<Product, String> {
}
