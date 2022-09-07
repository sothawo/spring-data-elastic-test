/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.so;

import org.springframework.data.elasticsearch.core.event.BeforeConvertCallback;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Component
public class FooBeforeConvertCallback implements BeforeConvertCallback<Foo> {

	@Override
	public Foo onBeforeConvert(Foo foo, IndexCoordinates indexCoordinates) {


		if (CollectionUtils.isEmpty(foo.getMap1())) {
			foo.setMap1(null);
		}

		if (CollectionUtils.isEmpty(foo.getMap2())) {
			foo.setMap2(null);
		}
		return foo;
	}
}
