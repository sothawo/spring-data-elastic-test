/*
 * (c) Copyright 2020 codecentric AG
 */
package com.sothawo.springdataelastictest.sampleentity;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SampleEntityRepository extends ElasticsearchRepository<SampleEntity, String> {
}