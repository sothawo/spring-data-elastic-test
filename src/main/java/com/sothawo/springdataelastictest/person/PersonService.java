/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.person;

import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchAggregation;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.search.aggregations.AggregationBuilders.*;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Service
public class PersonService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

	private final PersonRepository repository;
	private final ReactiveElasticsearchOperations operations;

	public PersonService(PersonRepository repository, ReactiveElasticsearchOperations operations) {
		this.repository = repository;
		this.operations = operations;
	}

	public Flux<Long> create(int count) {
		var template = (ReactiveElasticsearchTemplate) this.operations;
		var refreshPolicy = template.getRefreshPolicy();
		template.setRefreshPolicy(RefreshPolicy.NONE);

		return repository.deleteAll()
			.thenMany(Flux.range(1, count)
				.map(Person::create)
				.buffer(500)
				.flatMap(repository::saveAll)
				.map(Person::getId))
			.doOnComplete(() -> {
				template.setRefreshPolicy(refreshPolicy);
			});
	}

	public Flux<Person> all() {
		return repository.findAll();
	}

	public Flux<Person> allWithAge() {

		var query = Query.findAll();
		query.addFields("age");
		query.addSourceFilter(new FetchSourceFilterBuilder().withIncludes("*").build());

		return operations.search(query, Person.class).map(SearchHit::getContent);
	}

	public Flux<Pair<String, Long>> lastNameCounts() {
		var query = new NativeSearchQueryBuilder()
			.withQuery(matchAllQuery())
			.withAggregations(terms("lastNames").field("last-name")) //
			.build();

		return operations.aggregate(query, Person.class) //
			.map(aggregationContainer -> ((ElasticsearchAggregation) aggregationContainer).aggregation()) //
			.flatMap(aggregation -> //
				Flux.fromStream(((Terms) aggregation) //
					.getBuckets().stream() //
					.map(bucket -> Pair.of(bucket.getKeyAsString(), bucket.getDocCount()))));
	}

	public Mono<Person> byIdWithrouting(String id, String routing) {
		return repository.findByIdWithRouting(Long.valueOf(id), routing);
	}

	public Flux<SearchHit<Person>> test() {
		Query query = new CriteriaQuery(new Criteria("lastName").is("Moeller")).setPageable(PageRequest.of(0, 5));

		return operations.searchForHits(query, Person.class)
			.flatMapMany(reactiveSearchHits -> {
				LOGGER.info("total number of hits: {}", reactiveSearchHits.getTotalHits());
				return reactiveSearchHits.getSearchHits();
			});
	}
}
