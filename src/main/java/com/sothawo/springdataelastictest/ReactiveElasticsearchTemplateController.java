/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/template")
public class ReactiveElasticsearchTemplateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveElasticsearchTemplateController.class);

    private final ReactiveElasticsearchTemplate template;

    public ReactiveElasticsearchTemplateController(final ReactiveElasticsearchTemplate template) {
        this.template = template;
    }

    @PostMapping("/person")
    public String save(@RequestBody Person person) {

        final Mono<Person> mono = template.save(person);

        return mono.block().getId().toString();
    }

    @GetMapping("/person/{id}")
    public Mono<Person> findById(@PathVariable("id") final Long id) {
        return template.get(id.toString(), Person.class)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "not found")));
    }

    @GetMapping("/persons/{lastName}")
    public Flux<SearchHit<Person>> findByLastName(@PathVariable("lastName") String lastName) {
        Query query = new CriteriaQuery(new Criteria("lastName").is(lastName));
        return template.search(query, Person.class).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "not found")));
    }

    @GetMapping("/test")
    public Flux<SearchHit<Person>> test() {
        Query query = new NativeSearchQueryBuilder()
            .withQuery(QueryBuilders.wildcardQuery("id", "1*"))
            .build();

        return template.search(query, Person.class)
            .onErrorResume(throwable -> {
                LOGGER.error("{}: {}", throwable.getClass().getSimpleName(), throwable.getMessage());
                return Flux.empty();
            });
    }

    @GetMapping("/personsPage/{lastName}")
    public Mono<SearchPage<Person>> byNamePaged(@PathVariable("lastName") String lastName, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        Query query = new CriteriaQuery(new Criteria("lastName").is(lastName)).setPageable(PageRequest.of(page, 10));
        return template.searchForPage(query, Person.class);
    }

}
