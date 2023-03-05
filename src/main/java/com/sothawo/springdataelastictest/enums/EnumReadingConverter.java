package com.sothawo.springdataelastictest.enums;

import org.springframework.core.convert.converter.Converter;

public class EnumReadingConverter implements Converter<String, Enum<?>> {
	@Override
	public Enum<?> convert(String source) {
		return null;
	}
}
