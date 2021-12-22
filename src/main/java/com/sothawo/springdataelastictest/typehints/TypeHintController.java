/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.typehints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/typehints")
public class TypeHintController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TypeHintController.class);

	private final TypeHintRepository repository;

	public TypeHintController(TypeHintRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public void test() {

		List<BaseClass> docs = new ArrayList<>();

		DerivedOne docOne = new DerivedOne();
		docOne.setId("one");
		docOne.setBaseText("baseOne");
		docOne.setDerivedOne("derivedOne");
		docs.add(docOne);

		DerivedTwo docTwo = new DerivedTwo();
		docTwo.setId("two");
		docTwo.setBaseText("baseTwo");
		docTwo.setDerivedTwo("derivedTwo");
		docs.add(docTwo);

		repository.saveAll(docs);

		SearchHits<? extends BaseClass> searchHits = repository.searchAllBy();

		for (SearchHit<? extends BaseClass> searchHit : searchHits) {
			LOGGER.info(searchHit.toString());
		}

		int answer = 42;
	}
}
