    /*
     * (c) Copyright 2019 codecentric AG
     */
    package com.sothawo.springdataelastictest;

    import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
    import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository;
    import org.springframework.stereotype.Component;

    @Component
    public class PersonRepository extends SimpleElasticsearchRepository<Person, Long> {
        public PersonRepository(ElasticsearchOperations elasticsearchOperations) {
            super(elasticsearchOperations);
        }
    }
