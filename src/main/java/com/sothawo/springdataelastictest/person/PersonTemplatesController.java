/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.person;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.index.AliasAction;
import org.springframework.data.elasticsearch.core.index.AliasActionParameters;
import org.springframework.data.elasticsearch.core.index.AliasActions;
import org.springframework.data.elasticsearch.core.index.GetTemplateRequest;
import org.springframework.data.elasticsearch.core.index.PutTemplateRequest;
import org.springframework.data.elasticsearch.core.index.TemplateData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("templates")
public class PersonTemplatesController {

    private final ElasticsearchOperations operations;

    public PersonTemplatesController(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @GetMapping("/legacy/persons")
    public void createPersonsTemplate() {

        IndexOperations indexOperations = operations.indexOps(Person.class);

        String templateName = "person-template";
        PutTemplateRequest putTemplateRequest = PutTemplateRequest.builder(templateName, "persons-*")
            .withVersion(42)
            .withOrder(7)
            .withSettings(indexOperations.createSettings())
            .withMappings(indexOperations.createMapping())
            .withAliasActions(new AliasActions(new AliasAction.Add(AliasActionParameters.builderForTemplate().withAliases("person-alias").build())))
            .build();

        indexOperations.putTemplate(putTemplateRequest);

        TemplateData template = indexOperations.getTemplate(new GetTemplateRequest(templateName));

        int answer = 42;
    }
}
