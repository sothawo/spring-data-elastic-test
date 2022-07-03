/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.event;

import com.sothawo.springdataelastictest.sampleentity.SampleEntity;
import org.springframework.data.elasticsearch.core.event.BeforeConvertCallback;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Component
public class SampleEntityBeforeConvertCallback implements BeforeConvertCallback<SampleEntity> {

	@Override
	public SampleEntity onBeforeConvert(SampleEntity sampleEntity, IndexCoordinates indexCoordinates) {

		if (sampleEntity.getId() == null) {
			return sampleEntity.withId(UUID.randomUUID().toString());
		}

		return sampleEntity;
	}
}
