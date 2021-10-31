/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.so;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.stereotype.Component;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Component
public class FooMappingValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(FooMappingValidator.class);

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final ElasticsearchOperations operations;

	public FooMappingValidator(ElasticsearchOperations operations) {
		this.operations = operations;
	}

	@Autowired
	public void checkFooMapping() {

		var indexOperations = operations.indexOps(Foo.class);

		if (indexOperations.exists()) {
			LOGGER.info("checking if mapping for Foo changed");

			var mappingFromEntity = indexOperations.createMapping();
			var mappingFromEntityNode = objectMapper.valueToTree(mappingFromEntity);
			var mappingFromIndexNode = objectMapper.valueToTree(indexOperations.getMapping());

			if (!mappingFromEntityNode.equals(mappingFromIndexNode)) {
				LOGGER.info("mapping for class Foo changed!");
				indexOperations.putMapping(mappingFromEntity);
			}
		}
	}
}
