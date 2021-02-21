/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexInformation;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/indexinformation")
public class IndexInformationController {

    private final ElasticsearchOperations operations;

    public IndexInformationController(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @GetMapping("/{name}")
    public List<IndexInformation> get(@PathVariable String name) {
        return operations.indexOps(IndexCoordinates.of(name)).getInformation();
    }
}
