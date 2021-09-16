/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.person;

import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class PersonCustomRepositoryImpl extends AbstractRoutingRepository<Person, String> implements PersonCustomRepository {

	public PersonCustomRepositoryImpl(ReactiveElasticsearchOperations operations) {
		super(operations, Person.class);
	}

}
