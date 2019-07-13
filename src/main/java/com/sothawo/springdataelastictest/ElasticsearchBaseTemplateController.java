/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class ElasticsearchBaseTemplateController {

	private final ElasticsearchOperations elasticsearchOperations;

    private final IndexCoordinates index = IndexCoordinates.of("person");

	public ElasticsearchBaseTemplateController(final ElasticsearchOperations elasticsearchOperations) {
		this.elasticsearchOperations = elasticsearchOperations;
	}

	public String save(final Person person) {

		final IndexQuery indexQuery = new IndexQueryBuilder().withId(person.getId().toString()).withObject(person).build();

		final String documentId = elasticsearchOperations.index(indexQuery, index);

		return documentId;
	}

	public Person findById(final Long id) {
		final Person person = elasticsearchOperations.get(GetQuery.getById(id.toString()), Person.class, index);
		return person;
	}
}
