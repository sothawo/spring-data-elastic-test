/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.time.ZonedDateTime;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/logs")
public class LogController {

    private final LogRepository logRepository;
    private final ElasticsearchOperations operations;

    public LogController(LogRepository logRepository, ElasticsearchOperations operations) {
        this.logRepository = logRepository;
        this.operations = operations;
    }

    //    @Scheduled(initialDelay = 5, fixedDelay = 15_000)
    @PostMapping
    public String log() {
        LogEntity logEntity = new LogEntity();
        String text = "Logging at " + LocalTime.now().toString();
        logEntity.setText(text);
        logRepository.save(logEntity);
        return text;
    }

    @GetMapping("/{search}")
    SearchHits<LogEntity> search(@PathVariable String search) {
        return logRepository.searchByText(search);
    }

    @DeleteMapping
    public void delete() {
        logRepository.deleteAll();
    }

    @GetMapping("/between")
    SearchHits<LogEntity> lastWeek() {
        ZonedDateTime to = ZonedDateTime.now();
        ZonedDateTime from = to.minusDays(7);
        Criteria criteria = new Criteria("logTime").between(from, to);
        CriteriaQuery query = new CriteriaQuery(criteria);
//        operations.getElasticsearchConverter().updateQuery(query, LogEntity.class);
        return operations.search(query, LogEntity.class, IndexCoordinates.of("log-*"));
    }
}
