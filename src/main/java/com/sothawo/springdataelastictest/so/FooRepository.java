/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.so;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface FooRepository extends ElasticsearchRepository<Foo, String> {

    SearchHits<Foo> searchBy();

    @Query("{" +
        "        \"bool\" : {" +
        "          \"must\" : [" +
        "              { \"range\" : { \"start-time\" : { \"gte\": \"?0\" } } }," +
        "              { \"range\" : { \"end-time\" :   { \"lte\": \"?1\"  } } } " +
        "          ]" +
        "        }" +
        "}")
    SearchHits<Foo> search(LocalDateTime from, LocalDateTime to);

	List<Foo> findByJoinedDateBetween(LocalDate fromJoinedDate, LocalDate toJoinedDate);

}
