/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/logs")
public class LogController {

    private final LogRepository logRepository;

    public LogController(LogRepository logRepository) {
        this.logRepository = logRepository;
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
}
