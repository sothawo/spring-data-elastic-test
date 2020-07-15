/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ReactiveIndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.index.AliasAction;
import org.springframework.data.elasticsearch.core.index.AliasActionParameters;
import org.springframework.data.elasticsearch.core.index.AliasActions;
import org.springframework.data.elasticsearch.core.index.DeleteTemplateRequest;
import org.springframework.data.elasticsearch.core.index.GetTemplateRequest;
import org.springframework.data.elasticsearch.core.index.PutTemplateRequest;
import org.springframework.data.elasticsearch.core.index.TemplateData;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalTime;
import java.time.ZonedDateTime;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/logs")
public class LogController {

    private static final String TEMPLATE_NAME = "log-template";

    private final LogRepository logRepository;
    private final ReactiveElasticsearchOperations operations;
    private final ReactiveIndexOperations indexOps;

    public LogController(LogRepository logRepository, ReactiveElasticsearchOperations operations) {
        this.logRepository = logRepository;
        this.operations = operations;
        this.indexOps = operations.indexOps(LogEntity.class);
    }

    //    @Scheduled(initialDelay = 5, fixedDelay = 15_000)
    @PostMapping
    public Mono<String> log() {
        LogEntity logEntity = new LogEntity();
        String text = "Logging at " + LocalTime.now().toString();
        logEntity.setText(text);
        return logRepository.save(logEntity).map(LogEntity::getText);
    }

    @GetMapping("/{search}")
    Flux<SearchHit<LogEntity>> search(@PathVariable String search) {
        return logRepository.searchByText(search);
    }

    @DeleteMapping
    public Mono<Void> delete() {
        return logRepository.deleteAll().then();
    }

    @GetMapping("/between")
    Flux<SearchHit<LogEntity>> lastWeek() {
        ZonedDateTime to = ZonedDateTime.now();
        ZonedDateTime from = to.minusDays(7);
        Criteria criteria = new Criteria("logTime").between(from, to);
        CriteriaQuery query = new CriteriaQuery(criteria);
//        operations.getElasticsearchConverter().updateQuery(query, LogEntity.class);
        return operations.search(query, LogEntity.class, IndexCoordinates.of("log-*"));
    }

    @PostMapping("/template")
    public Mono<Boolean> putTemplate() {
        PutTemplateRequest.TemplateRequestBuilder builder = PutTemplateRequest.builder(TEMPLATE_NAME, "log-*")
            .withAliasActions(new AliasActions().add(
                new AliasAction.Add(AliasActionParameters.builderForTemplate()
                    .withAliases("log-all").build())));

        return indexOps.createSettings()
            .doOnNext(builder::withSettings)
            .flatMap(ignore -> indexOps.createMapping())
            .flatMap(mappings -> Mono.just(builder.withMappings(mappings).build()))
            .flatMap(indexOps::putTemplate);
    }

    @GetMapping("/template")
    public Mono<TemplateData> getTemplate() {
        return indexOps.getTemplate(new GetTemplateRequest(TEMPLATE_NAME));
    }

    @DeleteMapping("/template")
    public Mono<Boolean> deleteTemplate() {
        return indexOps.deleteTemplate(new DeleteTemplateRequest(TEMPLATE_NAME));
    }
}
