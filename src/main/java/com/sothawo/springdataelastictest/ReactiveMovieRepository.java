/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Mono;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface ReactiveMovieRepository extends ReactiveElasticsearchRepository<Movie, String> {

    Mono<Long> countByTitle(String title);

	Flux<Movie> findByTitle(String title);

	Flux<Movie> findByTitle(String title, Pageable pageable);

	Flux<Movie> findFirst3ByTitle(String title);

    Flux<Movie> findFirst3ByTitleOrderByYearDesc(String title);
}
