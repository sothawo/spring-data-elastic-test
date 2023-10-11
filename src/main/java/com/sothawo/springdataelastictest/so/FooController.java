/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.so;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.stream.Stream;


/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/foo")
public class FooController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FooController.class);

	private final FooRepository fooRepository;
	private final ElasticsearchOperations operations;

	public FooController(FooRepository fooRepository, ElasticsearchOperations operations) {
		this.fooRepository = fooRepository;
		this.operations = operations;
	}

	@PostMapping
	public Foo add(@RequestBody Foo foo) {
		return fooRepository.save(foo);
	}

	@RequestMapping("/{id}")
	public Foo get(@PathVariable String id) {
		return fooRepository.findById(id).orElse(null);
	}

	@GetMapping("/now")
	public Foo now() {
		var foo = new Foo();
		foo.setId("42");
		foo.setSomeDate(ZonedDateTime.now());
		return fooRepository.save(foo);
	}

	@GetMapping
	public SearchHits<Foo> all() {
		return fooRepository.searchBy();
	}

	@GetMapping("/test")
	public void test() {
			var foo = new Foo();

			foo.setId("1");
			foo.setText("immediate - default");
			fooRepository.save(foo);

			foo.setId("2");
			foo.setText("wait_until");
			fooRepository.save(foo, RefreshPolicy.WAIT_UNTIL);

			foo.setId("3");
			foo.setText("none");
			fooRepository.save(foo, RefreshPolicy.NONE);

			foo.setId("4");
			foo.setText("immediate - default");
			fooRepository.save(foo);
	}

	@GetMapping("/userquery/{id}")
	public SearchHits<Foo> userQuery(@PathVariable Integer id) {
		return fooRepository.getUserQuery(id);
	}

	@DeleteMapping
	public void deleteAll() {
		operations.delete(operations.matchAllQuery(), Foo.class);
	}

	@GetMapping("/generic")
	public SearchHits<GenericEntity> allGeneric() {
		var criteria = Criteria.where("need").is("alcohol");
		Query query = new CriteriaQuery(criteria);
		return operations.search(query, GenericEntity.class, IndexCoordinates.of("foo"));
	}
}
