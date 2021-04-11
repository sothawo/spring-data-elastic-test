/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.cluster.ClusterHealth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("system")
public class SystemController {

    private final ElasticsearchOperations operations;

    public SystemController(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @GetMapping("/cluster/health")
    public ClusterHealth clusterHealth() {
        return operations.cluster().health();
    }
}
