/*
 * (c) Copyright 2022 sothawo
 */
package com.sothawo.springdataelastictest.compositeid;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
class CompositeId {
	@Field(type = FieldType.Long)
	private Long id;
	@Field(type = FieldType.Keyword)
	private String name;

	public CompositeId() {
	}

	public CompositeId(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return getId() + "-" + getName();
	}
}
