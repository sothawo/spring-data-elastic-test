/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest.person;

import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.backend.elasticsearch7.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.backend.elasticsearch7.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.backend.elasticsearch7.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.search.aggregations.AggregationBuilders.*;

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
		NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
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
		NativeSearchQuery query = new NativeSearchQueryBuilder()
			.withQuery(matchAllQuery())
			.withAggregations(terms(aggsName).field("first-name")) //
			.build();
		query.setMaxResults(0);

		SearchHits<Person> searchHits = operations.search(query, Person.class, IndexCoordinates.of("person"));
		if (searchHits.hasAggregations()) {
			var aggregations = ((ElasticsearchAggregations) searchHits.getAggregations()).aggregations();
			Terms terms = aggregations.get(aggsName);
			terms.getBuckets().forEach(bucket -> {
				System.out.println("bucket " + bucket.getKeyAsString() + ", doc_count: " + bucket.getDocCount());
			});
		}
		return searchHits;
	}

	@GetMapping("/persons/count")
	public long count() {
		return operations.count(new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build(), index);
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
