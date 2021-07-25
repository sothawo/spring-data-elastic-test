/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.so;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
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

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/foo")
public class FooController {

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
		foo.setStartTime(LocalDateTime.of(2021, 7, 13, 6, 0, 0));
		foo.setEndTime(LocalDateTime.of(2021, 7, 13, 12, 13, 14));
		fooRepository.save(foo);

		fooRepository.search(
			LocalDateTime.of(2021, 7, 13, 0, 0, 0),
			LocalDateTime.of(2021, 7, 13, 23, 59, 59));

		AnalyzeRequest request = AnalyzeRequest.withGlobalAnalyzer("stop", "some text to analyse");
		ElasticsearchRestTemplate restTemplate = (ElasticsearchRestTemplate) this.operations;
		AnalyzeResponse analyzeResponse = restTemplate.execute(client -> client.indices().analyze(request, RequestOptions.DEFAULT));
	}
}
