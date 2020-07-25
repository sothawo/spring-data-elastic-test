/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ScrolledPage;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final ElasticsearchTemplate template;

    public ManyController(ElasticsearchTemplate template) {
        this.template = template;
    }

    @GetMapping("/create/{count}")
    public void create(@PathVariable("count") Long count) {

        String indexName = template.getPersistentEntityFor(Many.class).getIndexName();

        long maxId = count;
        long fromId = 1L;

        while (fromId < maxId) {
            long toId = Math.min(fromId + 1000, maxId);

            List<IndexQuery> indexQueries = LongStream.range(fromId, toId + 1)
                .mapToObj(Many::new)
                .map(many -> new IndexQueryBuilder()
                    .withId(many.getId().toString())
                    .withObject(many)
                    .withIndexName(indexName)
                    .build())
                .collect(Collectors.toList());


            template.bulkIndex(indexQueries);

            fromId += 1000L;
        }
    }

    @GetMapping("/count")
    public Long count() {

        NativeSearchQuery query = new NativeSearchQueryBuilder()
            .withQuery(matchAllQuery())
            .withPageable(PageRequest.of(0, 10_000))
            .build();

        long count = 0;

        Set<String> scrollIds = new LinkedHashSet<>();

        Page<Many> manyPage = template.startScroll(1_000, query, Many.class);
        while (manyPage.hasContent()) {
            count += manyPage.getContent().size();
            LOG.info("{}", count);
            String scrollId = ((ScrolledPage) manyPage).getScrollId();
            scrollIds.add(scrollId);
            manyPage = template.continueScroll(scrollId, 1_000, Many.class);
        }

        scrollIds.forEach(template::clearScroll);

        return count;
    }
}
