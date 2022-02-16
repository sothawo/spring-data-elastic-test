/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.so;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

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
	public void add(@RequestBody Foo foo) {
		fooRepository.save(foo);
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
	public List<Foo> test() {
		return Collections.emptyList();
	}

	@GetMapping("/userquery/{id}")
	public SearchHits<Foo> userQuery(@PathVariable Integer id) {
		return fooRepository.getUserQuery(id);
	}

	@DeleteMapping
	public void deleteAll() {
		Query query = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
		operations.delete(query, Foo.class);
	}
}
