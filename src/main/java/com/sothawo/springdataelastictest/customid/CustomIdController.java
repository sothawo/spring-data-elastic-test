/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.customid;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("custom-id")
public class CustomIdController {

    private final CustomIdRepository repository;

    public CustomIdController(CustomIdRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public SearchHits<CustomId> all() {
        return repository.searchBy();
    }

    @PostMapping
    public CustomId save(@RequestBody CustomId customId) {
        return repository.save(customId);
    }
}
