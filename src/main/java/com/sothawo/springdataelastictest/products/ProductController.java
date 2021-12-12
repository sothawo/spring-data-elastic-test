/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.products;

import com.sothawo.springdataelastictest.ResourceNotFoundException;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.data.elasticsearch.backend.elasticsearch7.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.backend.elasticsearch7.query.ScriptField;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository repository;
    private final ElasticsearchOperations operations;

    public ProductController(ProductRepository productRepository, ElasticsearchOperations operations) {
        this.repository = productRepository;
        this.operations = operations;
    }

    @PostMapping
    public Product save(@RequestBody Product product) {
        return repository.save(product);
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("factor", 1.2);
        Query query = new NativeSearchQueryBuilder()
            .withQuery(idsQuery().addIds(id))
            .withSourceFilter(new FetchSourceFilter(new String[] {"*" }, new String[] {}))
            .withScriptField(
                new ScriptField("gross-price", new Script(ScriptType.INLINE, "expression", "doc['net-price'] * factor", params)))
            .build();

        var searchHits = operations.search(query, Product.class);

        if (searchHits.getTotalHits() == 0) {
            throw new ResourceNotFoundException();
        }

        return searchHits.getSearchHit(0).getContent();
    }
}
