/*
 * (c) Copyright 2022 sothawo
 */
package com.sothawo.springdataelastictest.range;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.time.LocalDate;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface VacationRepository extends ElasticsearchRepository<Vacation, String> {

	SearchHits<Vacation> searchByDate(LocalDate date);
}
