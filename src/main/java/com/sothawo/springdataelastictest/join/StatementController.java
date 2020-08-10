/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.join;

import org.apache.lucene.search.join.ScoreMode;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.join.JoinField;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.join.query.JoinQueryBuilders.*;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/statements")
public class StatementController {

    private final StatementRepository repository;

    private final ReactiveElasticsearchOperations operations;

    public StatementController(StatementRepository repository, ReactiveElasticsearchOperations operations) {
        this.repository = repository;
        this.operations = operations;
    }

    @GetMapping
    Flux<SearchHit<Statement>> all() {
        return repository.searchAllBy();
    }

    @PostMapping("/init")
    void init() {
        repository.deleteAll();

        Statement savedWeather = repository.save(
            Statement.builder()
                .withText("How is the weather?")
                .withRelation(new JoinField<>("question"))
                .build()).block();

        Statement sunnyAnswer = repository.save(
            Statement.builder()
                .withText("sunny")
                .withRelation(new JoinField<>("answer", savedWeather.getId()))
                .build()).block();

        repository.save(
            Statement.builder()
                .withText("rainy")
                .withRelation(new JoinField<>("answer", savedWeather.getId()))
                .build()).block();

        repository.save(
            Statement.builder()
                .withText("I don't like the rain")
                .withRelation(new JoinField<>("comment", savedWeather.getId()))
                .build()).block();

        repository.save(
            Statement.builder()
                .withText("+1 for the sun")
                .withRelation(new JoinField<>("vote", sunnyAnswer.getId()))
                .build()).block();

        Statement vote2 = repository.save(
            Statement.builder()
                .withText("-1 for the sun")
                .withRelation(new JoinField<>("vote", sunnyAnswer.getId()))
                .build()).block();

        vote2.setText("-2 for the sun1");
        repository.save(vote2).block();

        repository.delete(vote2).block();
    }

    @GetMapping("/votes")
    Flux<SearchHit<Statement>> hasVotes() {
    NativeSearchQuery query = new NativeSearchQueryBuilder()
        .withQuery(hasChildQuery("vote", matchAllQuery(), ScoreMode.None))
        .build();

    return operations.search(query, Statement.class);
}
}
