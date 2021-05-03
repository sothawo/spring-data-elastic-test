/*
 * (c) Copyright 2020 codecentric AG
 */
package com.sothawo.springdataelastictest.sampleentity;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface SampleEntityRepository extends ReactiveElasticsearchRepository<SampleEntity, String> {
}
