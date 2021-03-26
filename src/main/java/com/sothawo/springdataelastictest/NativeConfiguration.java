/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.beans.factory.config.ObjectFactoryCreatingFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.core.event.AuditingEntityCallback;
import org.springframework.nativex.hint.AccessBits;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.TypeHint;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */

@NativeHint(
    types = {
        @TypeHint(types = HighlightField.class, access = AccessBits.DECLARED_METHODS),
        @TypeHint(types = {AuditingEntityCallback.class}),
//        @TypeHint(typeNames = {"com.sothawo.springdataelastictest.person.PersonRepositoryImpl"}, access = AccessBits.ALL),
        @TypeHint(types = {ObjectFactoryCreatingFactoryBean.class})
    }
)
@Configuration
public class NativeConfiguration {
}
