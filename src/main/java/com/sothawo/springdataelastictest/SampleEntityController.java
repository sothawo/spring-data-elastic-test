/*
 * (c) Copyright 2020 codecentric AG
 */
package com.sothawo.springdataelastictest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sample-entities")
public class SampleEntityController {

    private final SampleEntityRepository repository;

    public SampleEntityController(SampleEntityRepository sampleEntityRepository) {
        this.repository = sampleEntityRepository;
    }

    @PostMapping
    public Mono<SampleEntity> add(@RequestBody SampleEntity sampleEntity) {
        return repository.save(sampleEntity);
    }

    @GetMapping("/{id}")
    public Mono<SampleEntity> get(@PathVariable String id) {
        return repository.findById(id);
    }

    @GetMapping("/save")
    public void save() {
        final SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setId("42");
        sampleEntity.setMessage("This is a message");
        repository.save(sampleEntity).block();
    }

    @GetMapping("/load")
    public Mono<SampleEntity> load() {
        return repository.findById("42");
    }
}
