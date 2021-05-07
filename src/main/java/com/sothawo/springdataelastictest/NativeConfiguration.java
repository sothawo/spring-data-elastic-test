/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.beans.factory.config.ObjectFactoryCreatingFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.AuditingHandlerSupport;
import org.springframework.data.auditing.IsNewAwareAuditingHandler;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.core.event.AuditingEntityCallback;
import org.springframework.nativex.hint.AccessBits;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.ResourceHint;
import org.springframework.nativex.hint.TypeHint;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */

@NativeHint(
        types = {
                @TypeHint(types = HighlightField.class, access = AccessBits.DECLARED_METHODS),
                @TypeHint(typeNames = {"org.springframework.data.elasticsearch.config.PersistentEntitiesFactoryBean"}),
                @TypeHint(types = {ObjectFactoryCreatingFactoryBean.class}),
                @TypeHint(types = {AuditingEntityCallback.class}),
                @TypeHint(types = {AuditingHandlerSupport.class}),
                @TypeHint(types = {IsNewAwareAuditingHandler.class})
        },
        resources = {
                // for the Faker data
                @ResourceHint( patterns = {".*.yml", "en/.*.yml"}),

                @ResourceHint( patterns = {"org.springframework.security.messages"}, isBundle = true)

        }
)
@Configuration
public class NativeConfiguration {
}
