/*
 * (c) Copyright 2022 sothawo
 */
package com.sothawo.springdataelastictest.so;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class LocationTypeSearch {
	@Id
	private String id;

	@MultiField(
		mainField = @Field(type = FieldType.Text),
		otherFields = { @InnerField(suffix = "keyword", type = FieldType.Keyword) }
	)
	private String name;

	@Field(type=FieldType.Integer)
	private String level;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
}
