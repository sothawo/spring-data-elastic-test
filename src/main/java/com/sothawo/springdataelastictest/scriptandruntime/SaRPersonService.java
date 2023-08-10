package com.sothawo.springdataelastictest.scriptandruntime;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.RuntimeField;
import org.springframework.data.elasticsearch.core.query.ScriptData;
import org.springframework.data.elasticsearch.core.query.ScriptType;
import org.springframework.data.elasticsearch.core.query.ScriptedField;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaRPersonService {
    private final ElasticsearchOperations operations;
    private final SaRPersonRepository repository;

    public SaRPersonService(ElasticsearchOperations operations, SaRPersonRepository repository) {
        this.operations = operations;
        this.repository = repository;
    }

    public void save(List<Person> persons) {
        repository.saveAll(persons);
    }

    public SearchHits<Person> findAllWithAge() {

        var scriptedField = ScriptedField.of("age",
                ScriptData.of(b -> b
                        .withType(ScriptType.INLINE)
                        .withScript("""
                                Instant currentDate = Instant.ofEpochMilli(new Date().getTime()); 
                                Instant startDate = doc['birth-date'].value.toInstant();
                                return (ChronoUnit.DAYS.between(startDate, currentDate) / 365);
                                """)));

        // version 1: use a direct query
        var query = new StringQuery("""
                { "match_all": {} }
                """);
        query.addScriptedField(scriptedField);
        query.addSourceFilter(FetchSourceFilter.of(b -> b.withIncludes("*")));

        var result1 = operations.search(query, Person.class);

        // version 2: use the repository
        var result2 = repository.findAllBy(scriptedField);

        return result1;
    }

    public SearchHits<Person> findWithGenderAndMaxAge(String gender, Integer maxAge) {

        var runtimeField = new RuntimeField("age", "long", """
                                Instant currentDate = Instant.ofEpochMilli(new Date().getTime()); 
                                Instant startDate = doc['birth-date'].value.toInstant();
                                emit (ChronoUnit.DAYS.between(startDate, currentDate) / 365);
                """);

        // variant 1 : use a direct query
        var query = CriteriaQuery.builder(Criteria
                        .where("gender").is(gender)
                        .and("age").lessThanEqual(maxAge))
                .withRuntimeFields(List.of(runtimeField))
                .withFields("age")
                .withSourceFilter(FetchSourceFilter.of(b -> b.withIncludes("*")))
                .build();

        var result1 = operations.search(query, Person.class);

        // variant 2: use the repository
        var result2 = repository.findByGenderAndAgeLessThanEqual(gender, maxAge, runtimeField);

        return result1;
    }
}
