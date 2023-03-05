package com.sothawo.springdataelastictest.enums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class ManufacturerWritingConverter implements Converter<Manufacturer, String> {
	@Override
	public String convert(Manufacturer source) {
		return source.getDisplayName();
	}
}
