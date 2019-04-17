/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class ElasticsearchBaseTemplateController {

	private final ElasticsearchOperations elasticsearchOperations;

	public ElasticsearchBaseTemplateController(final ElasticsearchOperations elasticsearchOperations) {
		this.elasticsearchOperations = elasticsearchOperations;
	}

	public String save(final Person person) {

		final IndexQuery indexQuery = new IndexQueryBuilder().withId(person.getId().toString()).withObject(person).build();

		final String documentId = elasticsearchOperations.index(indexQuery);

		return documentId;
	}

	public Person findById(final Long id) {
		final Person person = elasticsearchOperations.queryForObject(GetQuery.getById(id.toString()), Person.class);
		return person;
	}
}
