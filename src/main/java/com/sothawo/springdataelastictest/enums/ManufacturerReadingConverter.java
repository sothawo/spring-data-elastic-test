package com.sothawo.springdataelastictest.enums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class ManufacturerReadingConverter implements Converter<String, Manufacturer> {
	@Override
	public Manufacturer convert(String source) {
		return Manufacturer.of(source);
	}
}
