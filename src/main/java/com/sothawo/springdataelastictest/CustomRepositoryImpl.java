/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Component
public class CustomRepositoryImpl<T> implements CustomRepository<T> {

    private final ElasticsearchOperations operations;

    public CustomRepositoryImpl(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @Override
    public T saveNoRefresh(T entity) {
        IndexQuery query = new IndexQueryBuilder().withObject(entity).build();
        operations.index(query, getIndexCoordinates(entity));
        return entity;
    }

    private IndexCoordinates getIndexCoordinates(T entity) {
        return operations.getElasticsearchConverter().getMappingContext()
            .getRequiredPersistentEntity(entity.getClass()).getIndexCoordinates();
    }
}
