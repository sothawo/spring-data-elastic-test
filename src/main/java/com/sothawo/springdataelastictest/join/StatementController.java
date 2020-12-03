/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.join;

import org.apache.lucene.search.join.ScoreMode;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.join.JoinField;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.join.query.JoinQueryBuilders.*;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/statements")
public class StatementController {

    private final StatementRepository repository;

    private final ElasticsearchOperations operations;

    public StatementController(StatementRepository repository, ElasticsearchOperations operations) {
        this.repository = repository;
        this.operations = operations;
    }

    @DeleteMapping("/clear")
    void clear() {
        repository.deleteAll();
    }

    @GetMapping
    SearchHits<Statement> all() {
        return repository.searchAllBy();
    }

//    @GetMapping("/{id}/{routing}")
//    Statement get(@PathVariable String id, @PathVariable(required = false) String routing) {
//        return routing != null ? operations.get(id, routing, Statement.class) : operations.get(id, Statement.class);
//    }

    @PostMapping
    Statement insert(@RequestBody Statement statement) {
        return repository.save(statement);
    }

    @DeleteMapping
    void deleteStatement(@RequestBody Statement statement) {
        repository.delete(statement);
    }

//    @DeleteMapping("/{id}/{routing}")
//    void deleteById(@PathVariable String id, @PathVariable(required = false) String routing) {
//        operations.delete(id, routing, Statement.class);
//        operations.indexOps(Statement.class).refresh();
//    }

    @PostMapping("/init")
    void init() {
        repository.deleteAll();

        Statement savedWeather = repository.save(
            Statement.builder()
                .withText("How is the weather?")
                .withRelation(new JoinField<>("question"))
                .build());

        Statement sunnyAnswer = repository.save(
            Statement.builder()
                .withText("sunny")
                .withRelation(new JoinField<>("answer", savedWeather.getId()))
                .build());

        repository.save(
            Statement.builder()
                .withText("rainy")
                .withRelation(new JoinField<>("answer", savedWeather.getId()))
                .build());

        repository.save(
            Statement.builder()
                .withText("I don't like the rain")
                .withRelation(new JoinField<>("comment", savedWeather.getId()))
                .build());

        repository.save(
            Statement.builder()
                .withText("+1 for the sun")
                .withRouting(savedWeather.getId())
                .withRelation(new JoinField<>("vote", sunnyAnswer.getId()))
                .build());

        Statement vote2 = repository.save(
            Statement.builder()
                .withText("-1 for the sun")
                .withRouting(savedWeather.getId())
                .withRelation(new JoinField<>("vote", sunnyAnswer.getId()))
                .build());

        vote2.setText("-2 for the sun1");
        repository.save(vote2);

        repository.delete(vote2);
    }

    @GetMapping("/votes")
SearchHits<Statement> hasVotes() {
    NativeSearchQuery query = new NativeSearchQueryBuilder()
        .withQuery(hasChildQuery("vote", matchAllQuery(), ScoreMode.None))
        .build();

    return operations.search(query, Statement.class);
}
}
