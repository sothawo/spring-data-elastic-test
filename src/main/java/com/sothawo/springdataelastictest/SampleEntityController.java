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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

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

    @GetMapping("/test")
    public void test() {
    }

    @GetMapping("/putWithObjects")
    public void putWithObjects() {
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setId("42");
        Map<String, Long> map = new LinkedHashMap<String, Long>();
        map.put("one", 1l);
        map.put("two", 2l);
        sampleEntity.setObjects(Arrays.asList(new Foo("foo"), new Bar("bar"), map));
        sampleEntityRepository.save(sampleEntity);
    }

    @GetMapping("/getWithObjects")
    public SampleEntity getWithObjects() {
        return sampleEntityRepository.findById("42").orElse(null);
    }

    static class Foo {
        private String foo;

        public Foo() {
        }

        public Foo(String foo) {
            this.foo = foo;
        }

        public String getFoo() {
            return foo;
        }

        public void setFoo(String foo) {
            this.foo = foo;
        }
    }

    static class Bar {
        private String bar;

        public Bar() {
        }

        public Bar(String bar) {
            this.bar = bar;
        }

        public String getBar() {
            return bar;
        }

        public void setBar(String bar) {
            this.bar = bar;
        }
    }
}
