/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.typehints;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "type-hints")
public abstract class BaseClass {

	@Id
	private String id;

	@Field(type = FieldType.Text)
	private String baseText;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBaseText() {
		return baseText;
	}

	public void setBaseText(String baseText) {
		this.baseText = baseText;
	}

	@Override
	public String toString() {
		return "BaseClass{" +
			"id='" + id + '\'' +
			", baseText='" + baseText + '\'' +
			'}';
	}
}
