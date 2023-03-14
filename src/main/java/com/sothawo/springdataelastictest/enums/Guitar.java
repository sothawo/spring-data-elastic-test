package com.sothawo.springdataelastictest.enums;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.ValueConverter;

@Document(indexName = "guitars")
public record Guitar(
	@Id String id,
	@Field(type = FieldType.Keyword)
	@ValueConverter(ManufacturerPropertyValueConverter.class)
	Manufacturer manufacturer,
	@Field(type = FieldType.Integer)
	Integer year) {
}
