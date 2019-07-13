/*
 * (c) Copyright 2020 codecentric AG
 */
package com.sothawo.springdataelastictest;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/sample-entities")
public class SampleEntityController {

    private final SampleEntityRepository sampleEntityRepository;

    public SampleEntityController(SampleEntityRepository sampleEntityRepository) {
        this.sampleEntityRepository = sampleEntityRepository;
    }

    @PostMapping
    public SampleEntity save(@RequestBody SampleEntity sampleEntity) {
        return sampleEntityRepository.save(sampleEntity);
    }

    @GetMapping("/{id}")
    @Nullable
    public SampleEntity getById(@PathVariable("id") String id) {
        return sampleEntityRepository.findById(id).orElse(null);
    }
}
