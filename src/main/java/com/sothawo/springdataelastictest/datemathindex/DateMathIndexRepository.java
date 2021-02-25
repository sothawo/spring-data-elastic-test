/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.datemathindex;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface DateMathIndexRepository extends ReactiveElasticsearchRepository<DateMathIndexEntity, String> {
}
