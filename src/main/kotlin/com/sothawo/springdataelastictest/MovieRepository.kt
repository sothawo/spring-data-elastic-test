/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
interface MovieRepository : ElasticsearchRepository<Movie, String> {

    fun findByTitle(title: String): List<Movie>

    fun findFirst3ByTitle(title: String): List<Movie>

    fun findFirst3ByTitleOrderByYearDesc(title: String): List<Movie>
}
