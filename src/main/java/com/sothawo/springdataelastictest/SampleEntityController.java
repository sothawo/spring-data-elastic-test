/*
 * (c) Copyright 2020 codecentric AG
 */
package com.sothawo.springdataelastictest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payload")
public class SampleEntityController {

    private final SampleEntityRepository sampleEntityRepository;

    public SampleEntityController(SampleEntityRepository sampleEntityRepository) {
        this.sampleEntityRepository = sampleEntityRepository;
    }

    @GetMapping("/save")
    public void save() {
        final SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setId("42");
        sampleEntity.setMessage("This is a message");
        sampleEntityRepository.save(sampleEntity).block();
    }

    @GetMapping("/load")
    public Mono<SampleEntity> load() {
        return sampleEntityRepository.findById("42");
    }
}
