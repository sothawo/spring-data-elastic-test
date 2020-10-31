/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.so;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/foo")
public class FooController {

    private final FooRepository fooRepository;

    public FooController(FooRepository fooRepository) {
        this.fooRepository = fooRepository;
    }

    @PostMapping
    public void add(@RequestBody Foo foo) {
        fooRepository.save(foo);
    }

    @RequestMapping("/{id}")
    public Foo get(@PathVariable String id) {
        return fooRepository.findById(id).orElse(null);
    }
}
