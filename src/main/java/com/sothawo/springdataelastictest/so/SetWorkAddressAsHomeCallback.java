/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.so;

import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.event.AfterConvertCallback;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Component
public class SetWorkAddressAsHomeCallback implements AfterConvertCallback<Foo> {
    @Override
    public Foo onAfterConvert(Foo foo, Document document, IndexCoordinates indexCoordinates) {
//        if (foo.workAddressSameAsHome) {
//            foo.home = foo.work;
//        }
        return foo;
    }
}
