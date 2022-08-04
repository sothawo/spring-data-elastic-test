/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.customid;

import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "custom-id")
public class CustomId {
	@Field(type = FieldType.Keyword)
	private String part1;

	@Field(type = FieldType.Keyword)
	private String part2;

	@Field(type = FieldType.Text)
	private String text;

	@Id
	@ReadOnlyProperty
	@AccessType(AccessType.Type.PROPERTY)
	public String getId() {
		return part1 + '-' + part2;
	}

	public String getPart1() {
		return part1;
	}

	public void setPart1(String part1) {
		this.part1 = part1;
	}

	public String getPart2() {
		return part2;
	}

	public void setPart2(String part2) {
		this.part2 = part2;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
