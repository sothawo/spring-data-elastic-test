/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping
    public String log() {
        LogEntity logEntity = new LogEntity();
        String text = "Logging at " + LocalTime.now().toString();
        logEntity.setText(text);
        logRepository.save(logEntity);
        return text;
    }

    @DeleteMapping
    public void delete() {
        logRepository.deleteAll();
    }
}
