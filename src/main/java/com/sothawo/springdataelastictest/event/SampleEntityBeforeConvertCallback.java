/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.event;

import com.sothawo.springdataelastictest.SampleEntity;
import org.elasticsearch.common.UUIDs;
import org.springframework.data.elasticsearch.core.event.BeforeConvertCallback;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Component
public class SampleEntityBeforeConvertCallback implements BeforeConvertCallback<SampleEntity> {

    @Override
    public SampleEntity onBeforeConvert(SampleEntity sampleEntity, IndexCoordinates indexCoordinates) {

        if (sampleEntity.getId() == null) {
            sampleEntity.setId(UUIDs.randomBase64UUID());
        }

        return sampleEntity;
    }
}
