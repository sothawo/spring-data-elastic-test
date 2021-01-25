/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.datemathindex;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/dmi")
public class DateMathIndexController {

    private final DateMathIndexRepository repository;

    public DateMathIndexController(DateMathIndexRepository repository) {
        this.repository = repository;
    }
}
