/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.core.SearchHit;
import reactor.core.publisher.Flux;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Mono;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface RecativeMovieRepository extends ReactiveElasticsearchRepository<Movie, String> {


    Mono<Long> countByTitle(String title);

    @Highlight(fields = {@HighlightField(name = "title")})
	Flux<SearchHit<Movie>> findByTitle(String title);

    @Highlight(fields = {@HighlightField(name = "title")})
	Flux<SearchHit<Movie>> findByTitle(String title, Pageable pageable);

    @Highlight(fields = {@HighlightField(name = "title")})
	Flux<SearchHit<Movie>> findFirst3ByTitle(String title);

    @Highlight(fields = {@HighlightField(name = "title")})
    Flux<SearchHit<Movie>> findFirst3ByTitleOrderByYearDesc(String title);
}
