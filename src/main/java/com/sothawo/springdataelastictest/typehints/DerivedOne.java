/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.typehints;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class DerivedOne extends BaseClass {
	@Field(type = FieldType.Text)
	private String derivedOne;

	public String getDerivedOne() {
		return derivedOne;
	}

	public void setDerivedOne(String derivedOne) {
		this.derivedOne = derivedOne;
	}

	@Override
	public String toString() {
		return "DerivedOne{" +
			"derivedOne='" + derivedOne + '\'' +
			"} " + super.toString();
	}
}
