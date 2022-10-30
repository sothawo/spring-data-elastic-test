/*
 * (c) Copyright 2022 sothawo
 */
package com.sothawo.springdataelastictest.so;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/ls")
public class LocationSearchController {

	private final ElasticsearchOperations operations;

	public LocationSearchController(ElasticsearchOperations operations) {
		this.operations = operations;
	}

	@GetMapping("/test")
	public void test() {
		operations.indexOps(LocationSearch.class).createWithMapping();
		operations.search(autoCompleteLocationQueryBuilder("pur", "1", 42L), LocationSearch.class);
	}

	public Query autoCompleteLocationQueryBuilder(String locationTerm, String level, Long tenantId){

		QueryBuilder tenantQuery = QueryBuilders
			.matchQuery("tenantId", tenantId);

		String regexExpression = ".*" + locationTerm + "*";
		QueryBuilder regexQuery = QueryBuilders.regexpQuery("name",regexExpression);

		String nestedPath="locationType";
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		MatchQueryBuilder matchQuery =
			QueryBuilders.matchQuery("locationType.level", level);

		NestedQueryBuilder nestedQuery = QueryBuilders
			.nestedQuery(nestedPath, boolQueryBuilder.must(matchQuery), ScoreMode.Avg);

		QueryBuilder finalQuery = QueryBuilders.boolQuery()
			.must(tenantQuery)
			.must(regexQuery)
			.must(nestedQuery);

		return new NativeSearchQueryBuilder()
			.withQuery(finalQuery)
			.build()
			.setPageable(PageRequest.of(0, 10));
	}
}
