package com.sothawo.springdataelastictest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public interface PersonRepository extends ElasticsearchRepository<Person, Long>, PersonCustomRepository {
    List<Person> findByLastName(final String lastName);

    SearchHits<Person> findByLastNameOrFirstName(String lastName, String firstName);

    @Highlight(fields = {@HighlightField(name = "firstName"), @HighlightField(name = "lastName")})
    SearchHits<Person> queryByLastNameOrFirstNameOrderByBirthDate(String lastName, String firstName);

    @Query(value = "{\"fuzzy\":{\"last-name\":\"?0\"}}")
    List<Person> findByLastNameFuzzy(final String lastName);

    List<Person> findAllByOrderByFirstName();

    Stream<SearchHit<Person>> findByBirthDateAfter(LocalDate date);

    SearchPage<Person> searchByLastName(String name, Pageable pageable);


    @Query("{\"match\": {\n" +
        "      \"first-name\": {\n" +
        "        \"query\": \"?0\",\n" +
        "        \"operator\": \"and\"\n" +
        "      }\n" +
        "    }}\n")
    SearchHits<Person> queryWithFirstName(String name);
}
