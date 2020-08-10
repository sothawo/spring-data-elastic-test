/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest.movies;

import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.stream.Stream;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface MovieRepository extends ElasticsearchRepository<Movie, String> {

    @Highlight(fields = {@HighlightField(name = "title")})
    Stream<SearchHit<Movie>> findBy();

    @Highlight(fields = {@HighlightField(name = "title")})
    SearchHits<Movie> findByTitle(String title);

    @Highlight(fields = {@HighlightField(name = "title")})
    SearchHits<Movie> findByTitleLike(String title);

    @Highlight(fields = {@HighlightField(name = "title")})
    SearchHits<Movie> findFirst3ByTitle(String title);

    @Highlight(fields = {@HighlightField(name = "title")})
    SearchHits<Movie> findFirst3ByTitleOrderByYearDesc(String title);
}
