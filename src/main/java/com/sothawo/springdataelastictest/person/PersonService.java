/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.person;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.AbstractReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.elasticsearch.client.elc.QueryBuilders.*;

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
		var template = (AbstractReactiveElasticsearchTemplate) this.operations;
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


		var query = NativeQuery.builder()
			.withQuery(matchAllQueryAsQuery())
			.withAggregation("lastNames", Aggregation.of(a -> a
				.terms(ta -> ta.field("last-name"))))
			.build();

		return operations.aggregate(query, Person.class) //
			.map(aggregationContainer -> ((ElasticsearchAggregation) aggregationContainer).aggregation()) //
			.filter(aggregation -> aggregation.getName().equals("lastNames"))
			.map(org.springframework.data.elasticsearch.client.elc.Aggregation::getAggregate)
			.flatMap(aggregate -> //
				Flux.fromStream(aggregate.sterms() //
					.buckets().array().stream() //
					.map(bucket -> Pair.of(bucket.key(), bucket.docCount()))));
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
