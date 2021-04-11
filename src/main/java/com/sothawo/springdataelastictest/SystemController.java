/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.cluster.ClusterHealth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("system")
public class SystemController {

    private final ReactiveElasticsearchOperations operations;

    public SystemController(ReactiveElasticsearchOperations operations) {
        this.operations = operations;
    }

    @GetMapping("/cluster/health")
    public Mono<ClusterHealth> clusterHealth() {
        return operations.cluster().health();
    }
}
