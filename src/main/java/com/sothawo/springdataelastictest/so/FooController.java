/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.so;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

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

	@GetMapping
	public SearchHits<Foo> all() {
		return fooRepository.searchBy();
	}

	@GetMapping("/test")
	public void test() {
		var foo = new Foo();
		foo.setId("4711");
		var bar1 = new Foo.Bar();
		bar1.setValue("bar-1");
		foo.addBar(bar1);
		var bar2 = new Foo.Bar();
		bar2.setValue("bar-2");
		foo.addBar(bar2);
		fooRepository.save(foo);

		var foundFoo = fooRepository.findById("4711").orElseThrow();

		LOGGER.info(foundFoo.toString());
	}
}
