/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface MovieRepository extends ElasticsearchRepository<Movie, String> {

    List<Movie> findByTitle(String title);

    List<Movie> findFirst3ByTitle(String title);

    List<Movie> findFirst3ByTitleOrderByYearDesc(String title);
}
