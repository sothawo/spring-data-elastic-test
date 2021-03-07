/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ReactiveIndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/playaround")
public class PlayAroundController {

    private final ReactiveElasticsearchOperations operations;

    public PlayAroundController(ReactiveElasticsearchOperations operations) {
        this.operations = operations;
    }

    @GetMapping
    public Mono<Document> playaround() {
        ReactiveIndexOperations indexOps = operations.indexOps(IndexCoordinates.of("playaround"));

        return indexOps.getMapping();
    }
}
