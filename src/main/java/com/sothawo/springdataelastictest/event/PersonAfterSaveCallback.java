/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.event;

import com.sothawo.springdataelastictest.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.event.AfterConvertCallback;
import org.springframework.data.elasticsearch.core.event.AfterSaveCallback;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Component
public class PersonAfterSaveCallback implements AfterSaveCallback<Person> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonAfterSaveCallback.class);


    @Override
    public Person onAfterSave(Person person, IndexCoordinates indexCoordinates) {
        LOGGER.debug("{} - {}", indexCoordinates, person);
        return person;
    }
}
