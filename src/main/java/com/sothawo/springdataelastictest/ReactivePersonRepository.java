package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHit;
import reactor.core.publisher.Flux;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface ReactivePersonRepository extends ReactiveElasticsearchRepository<Person, Long> {
    @Highlight(fields = {@HighlightField(name = "firstName"), @HighlightField(name = "lastName")})
    Flux<SearchHit<Person>> findTop10ByLastNameOrFirstName(String lastName, String firstName);

    @Query("{\"match\": {\n" +
        "      \"first-name\": {\n" +
        "        \"query\": \"?0\",\n" +
        "        \"operator\": \"and\"\n" +
        "      }\n" +
        "    }\n}")
    Flux<SearchHit<Person>> queryWithFirstName(String name);

}
