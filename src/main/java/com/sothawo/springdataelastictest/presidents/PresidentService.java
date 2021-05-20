/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.presidents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Service
public class PresidentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PresidentHandler.class);

    private final PresidentRepository repository;
    private final ReactiveElasticsearchOperations operations;

    public PresidentService(PresidentRepository repository,
                            ReactiveElasticsearchOperations operations) {
        this.repository = repository;
        this.operations = operations;
    }

    public Flux<President> load() {
        return repository.deleteAll()
            .thenMany(repository.saveAll(Arrays.asList(
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
                President.of("Joe Biden", 2021, 2025))));
    }

    public Mono<President> byId(String id) {
        return repository.findById(id);
    }

    public Flux<SearchHit<President>> searchByTerm(Integer year) {
        return repository.searchByTerm(year);
    }

    public Flux<SearchHit<President>> searchByName(String name, Boolean requestCache) {
        var query = new CriteriaQuery(new Criteria("name").is(name));
        if (requestCache != null) {
            query.setRequestCache(requestCache);
        }

        return operations.count(query, President.class)
            .flatMapMany(
                count -> {
                    LOGGER.info("#presidents: {}", count);
                    return operations.search(query, President.class);
                }
            );
    }
}
