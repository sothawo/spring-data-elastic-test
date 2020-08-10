/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest;

import com.sothawo.springdataelastictest.person.Person;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.index.AliasAction;
import org.springframework.data.elasticsearch.core.index.AliasActionParameters;
import org.springframework.data.elasticsearch.core.index.AliasActions;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/alias")
public class AliasController {

    private final ElasticsearchOperations operations;
    private final IndexOperations indexOps;

    public AliasController(ElasticsearchOperations operations) {
        this.operations = operations;
        this.indexOps = operations.indexOps(IndexCoordinates.of("dont-care"));
    }

    @PostMapping("/{index}/{alias}")
    public boolean create(@PathVariable String index, @PathVariable String alias) {

        AliasActions aliasActions = new AliasActions();
        aliasActions.add(new AliasAction.Add(AliasActionParameters.builder().withIndices(index).withAliases(alias).build()));
        return indexOps.alias(aliasActions);
    }


    @DeleteMapping("/{index}/{alias}")
    public boolean delete(@PathVariable String index, @PathVariable String alias) {

        AliasActions aliasActions = new AliasActions();
        aliasActions.add(new AliasAction.Remove(AliasActionParameters.builder().withIndices(index).withAliases(alias).build()));
        return indexOps.alias(aliasActions);
    }

    @PostMapping("/person/firstName/{firstName}")
    public boolean createPersonWithFilter(@PathVariable String firstName) {

        AliasActions aliasActions = new AliasActions();
        Query query = new CriteriaQuery(new Criteria("firstName").is(firstName));
        aliasActions.add(new AliasAction.Add(AliasActionParameters.builder()
            .withIndices("person")
            .withAliases(firstName.toLowerCase())
            .withFilterQuery(query, Person.class)
            .build()));
        return indexOps.alias(aliasActions);
    }
}
