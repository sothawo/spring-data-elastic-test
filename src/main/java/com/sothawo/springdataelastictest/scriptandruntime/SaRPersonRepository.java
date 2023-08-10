package com.sothawo.springdataelastictest.scriptandruntime;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.RuntimeField;
import org.springframework.data.elasticsearch.core.query.ScriptedField;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SaRPersonRepository extends ElasticsearchRepository<Person, String> {

    SearchHits<Person> findAllBy(ScriptedField scriptedField);
    SearchHits<Person> findByGenderAndAgeLessThanEqual(String gender, Integer age, RuntimeField runtimeField);
}
