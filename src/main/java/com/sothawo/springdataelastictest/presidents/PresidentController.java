/*
 * (c) Copyright 2020 codecentric AG
 */
package com.sothawo.springdataelastictest.presidents;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("presidents")
public class PresidentController {

    private final PresidentRepository repository;

    public PresidentController(PresidentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/load")
    public void load() {
        repository.saveAll(Arrays.asList(
                President.of("Dwight D Eisenhower", 1953, 1961),
                President.of("Lyndon B Johnson", 1963, 1969),
                President.of("Richard Nixon", 1969, 1974),
                President.of("Gerald Ford", 1974, 1977),
                President.of("Jimmy Carter", 1977, 1981),
                President.of("Ronald Reagen", 1981, 1989),
                President.of("George Bush", 1989, 1993),
                President.of("Bill Clinton", 1993, 2001),
                President.of("George W Bush", 2001, 2009),
                President.of("Barack Obama", 2009, 2017),
                President.of("Donald Trump", 2017, 2021)));
    }

    @GetMapping("/term/{year}")
    public SearchHits<President> searchByTerm(@PathVariable Integer year) {
        return repository.searchByTerm(year);
    }
}
