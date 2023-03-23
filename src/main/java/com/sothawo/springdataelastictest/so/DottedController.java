/*
 * (c) Copyright 2023 sothawo
 */
package com.sothawo.springdataelastictest.so;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/dotted")
public class DottedController {
	private final ElasticsearchOperations operations;
	private final FooRepository repository;

	public DottedController(ElasticsearchOperations operations, FooRepository repository) {
		this.operations = operations;
		this.repository = repository;
	}

	@PostMapping
	public Foo save(@RequestBody Foo foo) {
		return
			operations.save(foo);
	}

	@GetMapping("/{id}")
	public Foo get(@PathVariable String id) {
		return operations.get(id, Foo.class);
	}

	@GetMapping("/search1/{text}")
	public SearchHits<Foo> search1(@PathVariable String text) {
		return repository.searchByDottedTextQuery(text);
	}

	@GetMapping("/search4/{text}")
	public SearchHits<Foo> search4(@PathVariable String text) {
		return repository.searchByDottedText(text);
	}

	@GetMapping("/search2/{text}")
	public SearchHits<Foo> search2(@PathVariable String text) {
		return operations.search(CriteriaQuery.builder(
				Criteria.where("dottedText").is(text))
			.build(), Foo.class);
	}

	@GetMapping("/search3/{text}")
	public SearchHits<Foo> search3(@PathVariable String text) {
		return operations.search(NativeQuery.builder()
			.withQuery(QueryBuilders.match(b -> b.field("dotted.text").query(text)))
			.build(), Foo.class);
	}
}
