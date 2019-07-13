/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import reactor.core.publisher.Mono;

import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
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
@RequestMapping("/template")
public class ReactiveElasticsearchTemplateController {

	private final ReactiveElasticsearchTemplate reactiveElasticsearchTemplate;

	public ReactiveElasticsearchTemplateController(final ReactiveElasticsearchTemplate reactiveElasticsearchTemplate) {
		this.reactiveElasticsearchTemplate = reactiveElasticsearchTemplate;
	}

	@PostMapping("/person")
	public String save(@RequestBody Person person) {

		final Mono<Person> mono = reactiveElasticsearchTemplate.save(person);

		return mono.block().getId().toString();
	}

	@GetMapping("/person/{id}")
	public Person findById(@PathVariable("id") final Long id) {
		return reactiveElasticsearchTemplate.findById(id.toString(), Person.class, null, null).block();
	}
}
