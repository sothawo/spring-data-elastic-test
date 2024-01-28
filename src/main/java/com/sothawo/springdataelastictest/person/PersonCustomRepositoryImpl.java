/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.person;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.Query;

import static org.springframework.data.elasticsearch.client.elc.Queries.matchQueryAsQuery;


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

        Query query = NativeQuery.builder()
                .withAggregation("lastNames", Aggregation.of(a -> a
                        .terms(ta -> ta.field("last-name").size(10)))) //
                .withQuery(matchQueryAsQuery("first-name", firstName, null, null))
                .withPageable(pageable)
                .build();

        SearchHits<Person> searchHits = operations.search(query, Person.class);

        return SearchHitSupport.searchPageFor(searchHits, pageable);
    }
}
