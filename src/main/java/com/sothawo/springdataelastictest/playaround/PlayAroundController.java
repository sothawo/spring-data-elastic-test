/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.playaround;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/playaround")
public class PlayAroundController {

    private final ElasticsearchOperations operations;

    public PlayAroundController(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @GetMapping
    public Map<String, Object> playaround() {
        IndexOperations indexOps = operations.indexOps(IndexCoordinates.of("playaround"));

        return indexOps.getMapping();
    }
}
