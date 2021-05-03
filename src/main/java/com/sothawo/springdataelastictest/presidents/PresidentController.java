/*
 * (c) Copyright 2020 codecentric AG
 */
package com.sothawo.springdataelastictest.presidents;

import com.sothawo.springdataelastictest.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("presidents")
public class PresidentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PresidentController.class);

    private final PresidentRepository repository;
    private final ElasticsearchOperations operations;

    public PresidentController(PresidentRepository repository, ElasticsearchOperations operations) {
        this.repository = repository;
        this.operations = operations;
    }

    @GetMapping("/load")
    public Iterable<President> load() {
        repository.deleteAll();
        return repository.saveAll(Arrays.asList(
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
            President.of("Donald Trump", 2017, 2021),
            President.of("Joe Biden", 2021, 2025)));
    }

    @GetMapping("/term/{year}")
    public SearchHits<President> searchByTerm(@PathVariable Integer year) {
        return repository.searchByTerm(year);
    }

    @GetMapping("/name/{name}")
    public SearchHits<President> searchByName(@PathVariable String name, @Nullable @RequestParam(required = false) Boolean requestCache) {
        var query = new CriteriaQuery(new Criteria("name").is(name));
        if (requestCache != null) {
            query.setRequestCache(requestCache);
        }

        var count = operations.count(query, President.class);
        LOGGER.info("#presidents: {}", count);

        return operations.search(query, President.class);
    }

    @GetMapping("/{id}")
    @Nullable
    public President byId(@PathVariable String id) {

        var president = operations.get(id, President.class);
        if (president == null) {
            throw new ResourceNotFoundException();
        }
        return president;
    }
}
