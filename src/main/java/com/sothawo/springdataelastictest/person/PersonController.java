/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.person;

import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.ScriptField;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonRepository repository;
    private final ElasticsearchOperations operations;

    public PersonController(PersonRepository repository, ElasticsearchOperations operations) {
        this.repository = repository;
        this.operations = operations;
    }

    @GetMapping("/create/{count}")
    public void create(@PathVariable("count") Long count) {

        repository.deleteAll();

        long maxId = count;
        long fromId = 1L;

        while (fromId < maxId) {
            long toId = Math.min(fromId + 1000, maxId);

            List<Person> persons = LongStream.range(fromId, toId + 1)
                .mapToObj(Person::create)
                .collect(Collectors.toList());

            repository.saveAll(persons);

            fromId += 1000L;
        }
    }

    @GetMapping("/all")
    public Stream<Person> all() {
        return StreamSupport.stream(repository.findAll().spliterator(), false);
    }

    @GetMapping("/all-with-age")
    public Stream<Person> allWithAge() {

        var query = new NativeSearchQueryBuilder()
            .withQuery(matchAllQuery())
            .withFields("*")
            .withScriptField(new ScriptField("age",
                new Script(
                    ScriptType.INLINE, "painless",
                    """
                    Instant currentDate = Instant.ofEpochMilli(new Date().getTime());
                    Instant startDate = doc['birth-date'].value.toInstant();
                    ChronoUnit.DAYS.between(startDate, currentDate) / 365;
                    """,
                    Collections.emptyMap()
                )))
            .build();

        var searchHits = operations.search(query, Person.class);
        return searchHits.get().map(SearchHit::getContent);
    }

}
