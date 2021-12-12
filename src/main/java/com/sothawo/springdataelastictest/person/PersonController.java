/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.person;

import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.backend.elasticsearch7.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonRepository repository;
    private final ElasticsearchOperations operations;

    public PersonController(PersonRepository repository, ElasticsearchOperations operations) {
        this.repository = repository;
        this.operations = operations;
    }

    @GetMapping("/create/{count}")
    public void create(@PathVariable("count") Long count) {

        repository.deleteAll();

        long maxId = count;
        long fromId = 1L;

        while (fromId < maxId) {
            long toId = Math.min(fromId + 1000, maxId);

            List<Person> persons = LongStream.range(fromId, toId + 1)
                .mapToObj(Person::create)
                .collect(Collectors.toList());

            repository.saveAll(persons);

            fromId += 1000L;
        }
    }

    @GetMapping("/all")
    public Stream<Person> all() {
        return StreamSupport.stream(repository.findAll().spliterator(), false);
    }

    @GetMapping("/all-with-age")
    public Stream<Person> allWithAge() {

        var query = Query.findAll();
        query.addFields("age");
        query.addSourceFilter(new FetchSourceFilterBuilder().withIncludes("*").build());

        var searchHits = operations.search(query, Person.class);
        return searchHits.get().map(SearchHit::getContent);
    }

	@GetMapping("/{name}")
	public SearchHits<Person> byName(@PathVariable("name") final String name) {
		return repository.queryByLastNameOrFirstNameOrderByBirthDate(name, name);
	}

	@GetMapping("/fuzzy/count/{name}")
	public Long countByNameFuzzy(@PathVariable("name") String name) {
			return repository.countByLastNameFuzzy(name);
	}
	@GetMapping("/fuzzy/{name}")
	public SearchPage<Person> byNameFuzzy(@PathVariable("name") String name) {
			return repository.findByLastNameFuzzy(name, Pageable.unpaged());
	}

	@GetMapping("/firstNameWithLastNameCounts/{firstName}")
	public Pair<List<SearchHit<Person>>, Map<String, Long>> firstNameWithLastNameCounts(@PathVariable("firstName") String firstName) {

		// helper function to get the lastName counts from an Elasticsearch Aggregations
		Function<Aggregations, Map<String, Long>> getLastNameCounts = aggregations -> {
			Aggregation lastNames = aggregations.get("lastNames");
			if (lastNames != null) {
				List<? extends Terms.Bucket> buckets = ((Terms) lastNames).getBuckets();
				if (buckets != null) {
					return buckets.stream().collect(Collectors.toMap(Terms.Bucket::getKeyAsString, Terms.Bucket::getDocCount));
				}
			}
			return Collections.emptyMap();
		};

		Map<String, Long> lastNameCounts = null;
		List<SearchHit<Person>> searchHits = new ArrayList<>();

		Pageable pageable = PageRequest.of(0, 1000);
		boolean fetchMore = true;
		while (fetchMore) {
			SearchPage<Person> searchPage = repository.findByFirstNameWithLastNameCounts(firstName, pageable);

			if (lastNameCounts == null) {
				Aggregations aggregations = ((ElasticsearchAggregations) searchPage.getSearchHits().getAggregations()).aggregations();
				lastNameCounts = getLastNameCounts.apply(aggregations);
			}

			if (searchPage.hasContent()) {
				searchHits.addAll(searchPage.getContent());
			}

			pageable = searchPage.nextPageable();
			fetchMore = searchPage.hasNext();
		}

		return Pair.of(searchHits, lastNameCounts);
	}
}
