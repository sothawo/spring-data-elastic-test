/*
 * (c) Copyright 2020 codecentric AG
 */
package com.sothawo.springdataelastictest.presidents;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PresidentRepository extends ElasticsearchRepository<President, String> {
    SearchHits<President> searchByTerm(Integer year);
}
