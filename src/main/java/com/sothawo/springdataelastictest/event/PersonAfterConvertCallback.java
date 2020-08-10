/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.event;

import com.sothawo.springdataelastictest.person.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.event.AfterConvertCallback;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Component
public class PersonAfterConvertCallback implements AfterConvertCallback<Person> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonAfterConvertCallback.class);

    @Override
    public Person onAfterConvert(Person person, Document document, IndexCoordinates indexCoordinates) {
        LOGGER.debug("{} - {} - {}", indexCoordinates, document, person);
        return person;
    }
}
