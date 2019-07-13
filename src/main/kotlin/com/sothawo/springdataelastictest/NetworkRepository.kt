/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface NetworkRepository : ElasticsearchRepository<Network, String> {
    fun findByCidr(ip: String): List<Network>
}
