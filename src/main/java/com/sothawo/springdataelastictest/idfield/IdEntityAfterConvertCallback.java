package com.sothawo.springdataelastictest.idfield;

import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.event.AfterConvertCallback;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

@Component
public class IdEntityAfterConvertCallback implements AfterConvertCallback<IdEntity> {

	@Override
	public IdEntity onAfterConvert(IdEntity entity, Document document, IndexCoordinates indexCoordinates) {
		entity.setRealId(document.getId());
		return entity;
	}
}
