/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class ElasticsearchBaseTemplateController {

	private final ElasticsearchOperations elasticsearchOperations;

	public ElasticsearchBaseTemplateController(final ElasticsearchOperations elasticsearchOperations) {
		this.elasticsearchOperations = elasticsearchOperations;
	}

	public String save(@RequestBody Person person) {

		final IndexQuery indexQuery = new IndexQueryBuilder().withId(person.getId().toString()).withObject(person).build();

		final String documentId = elasticsearchOperations.index(indexQuery);

		return documentId;
	}
}
