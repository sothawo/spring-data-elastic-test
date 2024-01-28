/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest.person;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.elasticsearch.client.elc.Queries.matchAllQueryAsQuery;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/template")
public class PersonTemplateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonTemplateController.class);

    private final ElasticsearchOperations operations;
    private final IndexCoordinates index = IndexCoordinates.of("person");

    public PersonTemplateController(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @PostMapping("/person")
    public String save(@RequestBody Person person) {
        IndexQuery indexQuery = new IndexQueryBuilder().withId(person.getId().toString()).withObject(person).build();
        return operations.index(indexQuery, index);
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> findById(@PathVariable("id") final Long id) {
        return ResponseEntity.of(Optional.ofNullable(operations.get(id.toString(), Person.class, index)));

    }

    @GetMapping("/persons")
    public SearchHits<Person> persons() {
        BaseQuery query = (BaseQuery) operations.matchAllQuery();
        long count = operations.count(query, index);
        query.setPageable(PageRequest.of(0, (int) count));
        return operations.search(query, Person.class, index);
    }

    @GetMapping("/persons/{name}")
    public SearchHits<Person> personByName(@PathVariable String name) {
        ElasticsearchConverter elasticsearchConverter = operations.getElasticsearchConverter();
        Criteria criteria = new Criteria("lastName").is(name);
        CriteriaQuery query = new CriteriaQuery(criteria);
        query.setExplain(true);

        return operations.search(query, Person.class, index);
    }

    @GetMapping("/persons/aggs")
    public SearchHits<Person> aggregationsTest() {
        String aggsName = "first_names";
        BaseQuery query = NativeQuery.builder()
                .withQuery(matchAllQueryAsQuery())
                .withAggregation(aggsName, Aggregation.of(a -> a
                        .terms(ta -> ta.field("first-name"))))
                .build();
        query.setMaxResults(0);

        SearchHits<Person> searchHits = operations.search(query, Person.class, IndexCoordinates.of("person"));
        if (searchHits.hasAggregations()) {
            var aggregations = ((ElasticsearchAggregations) searchHits.getAggregations()).aggregations();
            aggregations.forEach(elasticsearchAggregation -> {
                var aggregation = elasticsearchAggregation.aggregation();
                if (aggregation.getName().equals(aggsName)) {
                    var aggregate = aggregation.getAggregate();

                    if (aggregate.isSterms()) {
                        aggregate.sterms().buckets().array().forEach(bucket -> {
                            System.out.println("bucket " + bucket.key() + ", doc_count: " + bucket.docCount());
                        });
                    }

                    if (aggregate.isDterms()) {
                        aggregate.dterms().buckets().array().forEach(bucket -> {
                            System.out.println("bucket " + bucket.key() + ", doc_count: " + bucket.docCount());
                        });
                    }

                    if (aggregate.isLterms()) {
                        aggregate.lterms().buckets().array().forEach(bucket -> {
                            System.out.println("bucket " + bucket.key() + ", doc_count: " + bucket.docCount());
                        });
                    }
                }
            });
        }
        return searchHits;
    }

    @GetMapping("/persons/count")
    public long count() {
        return operations.count(operations.matchAllQuery(), index);
    }

    @GetMapping("/test")
    public void test() {
        var person = new Person();
        person.setInternalId(42L);
        person.setFirstName("John");
        person.setLastName("Doe");
        var electricCar = new Car.ElectricCar();
        electricCar.setModel("Elecar");
        electricCar.setRange(500);
        var combustionCar = new Car.CombustionCar();
        combustionCar.setModel("Oily");
        combustionCar.setFuelType("Diesel");
        person.setCars(List.of(electricCar, combustionCar));

        var saved = operations.save(person);
        LOGGER.info("saved: {}", saved.toString());

        var loaded = operations.get(saved.getId().toString(), Person.class);
        LOGGER.info("loaded: {}", loaded.toString());
    }
}
