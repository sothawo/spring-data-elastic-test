/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.bigdecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/bigdecimal")
public class BigDecimalTestController {

	private static Logger LOGGER = LoggerFactory.getLogger(BigDecimalTestController.class);

	private final ElasticsearchOperations operations;

	public BigDecimalTestController(ElasticsearchOperations operations) {
		this.operations = operations;
	}

	@GetMapping
	public void test(){
		var savedInt = operations.save(new IntegerWrapper());
		var loadedInt = operations.get(savedInt.id, IntegerWrapper.class);
		LOGGER.info(loadedInt.toString());

		var savedBigDecimal = operations.save(new BigDecimalWrapper());
		var loadedBigDecimal = operations.get(savedBigDecimal.id, BigDecimalWrapper.class);
		LOGGER.info(loadedBigDecimal.toString());
	}

	@Document(indexName = "bigdecimal-wrapper")
	static class BigDecimalWrapper{
		@Id
		String id;
		BigDecimal one = BigDecimal.ONE;
		BigDecimal ten = BigDecimal.TEN;
		String someString = "yeah";

		@Override
		public String toString() {
			return "BigDecimalWrapper{" +
				"id='" + id + '\'' +
				", one=" + one +
				", ten=" + ten +
				", someString='" + someString + '\'' +
				'}';
		}
	}

	@Document(indexName = "integer-wrapper")
	static class IntegerWrapper{
		@Id
		String id;
		Integer one = 1;
		int ten = 10;
		String someString = "yeah";

		@Override
		public String toString() {
			return "IntegerWrapper{" +
				"id='" + id + '\'' +
				", one=" + one +
				", ten=" + ten +
				", someString='" + someString + '\'' +
				'}';
		}
	}
}
