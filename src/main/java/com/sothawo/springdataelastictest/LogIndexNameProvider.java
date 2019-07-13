/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Component
public class LogIndexNameProvider {

    public String timeSuffix() {
        return LocalTime.now().truncatedTo(ChronoUnit.MINUTES).toString().replace(':', '-');
    }
}
