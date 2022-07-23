/*
 * (c) Copyright 2022 sothawo
 */
package com.sothawo.springdataelastictest.compositeid;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class CompositeIdConverter {

	@ReadingConverter
	public static class Reader implements Converter<String, CompositeId> {
		@Override
		public CompositeId convert(String source) {
			var parts = source.split("-");
			if (parts.length != 2) {
				throw new IllegalArgumentException("invalid composite id source: " + source);
			}
			return new CompositeId(Long.parseLong(parts[0]), parts[1]);
		}
	}

	@WritingConverter
	public static class Writer implements Converter<CompositeId, String> {

		@Override
		public String convert(CompositeId source) {
			return source.getId() + "-" + source.getName();
		}
	}
}
