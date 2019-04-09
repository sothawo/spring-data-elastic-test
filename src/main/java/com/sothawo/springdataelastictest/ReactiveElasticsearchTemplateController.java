/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/reactive")
public class ReactiveElasticsearchTemplateController {

    private final ReactiveElasticsearchTemplate reactiveElasticsearchTemplate;

	public ReactiveElasticsearchTemplateController(final ReactiveElasticsearchTemplate reactiveElasticsearchTemplate) {
		this.reactiveElasticsearchTemplate = reactiveElasticsearchTemplate;
	}

	@PostMapping("/person")
	public String hello(@RequestBody Person person) {


		final Mono<Person> mono = reactiveElasticsearchTemplate.save(person);

		return mono.block().getId().toString();
	}
}
