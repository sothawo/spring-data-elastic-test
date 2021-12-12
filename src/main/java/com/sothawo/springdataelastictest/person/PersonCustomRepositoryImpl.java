/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.person;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.backend.elasticsearch7.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.Query;

import static org.elasticsearch.search.aggregations.AggregationBuilders.*;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class PersonCustomRepositoryImpl implements PersonCustomRepository {

    private final ElasticsearchOperations operations;

    public PersonCustomRepositoryImpl(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @Override
    public SearchPage<Person> findByFirstNameWithLastNameCounts(String firstName, Pageable pageable) {

        Query query = new NativeSearchQueryBuilder().withAggregations(terms("lastNames").field("last-name").size(10)) //
            .withQuery(QueryBuilders.matchQuery("first-name", firstName))
            .withPageable(pageable)
            .build();

        SearchHits<Person> searchHits = operations.search(query, Person.class);

        return SearchHitSupport.searchPageFor(searchHits, pageable);
    }
}
