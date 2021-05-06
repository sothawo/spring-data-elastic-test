package com.sothawo.springdataelastictest.products

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface ProductRepository : ElasticsearchRepository<Product, String>
