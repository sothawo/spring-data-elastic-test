/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.many;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.backend.elasticsearch7.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.backend.elasticsearch7.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.backend.elasticsearch7.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchScrollHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("many")
public class ManyController {

    private static final Logger LOG = LoggerFactory.getLogger(ManyController.class);

    private final ElasticsearchRestTemplate template;

    public ManyController(ElasticsearchRestTemplate template) {
        this.template = template;
    }

    @GetMapping("/create/{count}")
    public void create(@PathVariable("count") Long count) {

        long maxId = count;
        long fromId = 1L;

        while (fromId < maxId) {
            long toId = Math.min(fromId + 1000, maxId);

            List<Many> manys = LongStream.range(fromId, toId + 1)
                .mapToObj(Many::new)
                .collect(Collectors.toList());

            template.save(manys);

            fromId += 1000L;
        }
    }

    @GetMapping("/count")
    public Long count() {
        IndexCoordinates indexCoordinates = template.getElasticsearchConverter().getMappingContext().getPersistentEntity(Many.class).getIndexCoordinates();
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();

        long count = 0;

        Set<String> scrollIds = new LinkedHashSet<>();

        SearchScrollHits<Many> searchHits = template.searchScrollStart(1_000, query, Many.class, indexCoordinates);
        while (!searchHits.getSearchHits().isEmpty()) {
            count += searchHits.getSearchHits().size();
            LOG.info("{}", count);
            String scrollId = searchHits.getScrollId();
            scrollIds.add(scrollId);
            searchHits = template.searchScrollContinue(scrollId, 1_000, Many.class, indexCoordinates);
        }
        template.searchScrollClear(new ArrayList<>(scrollIds));
        return count;
    }
}
