/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.typehints;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class DerivedTwo extends BaseClass {
	@Field(type = FieldType.Text)
	private String derivedTwo;

	public String getDerivedTwo() {
		return derivedTwo;
	}

	public void setDerivedTwo(String derivedTwo) {
		this.derivedTwo = derivedTwo;
	}

	@Override
	public String toString() {
		return "DerivedTwo{" +
				"derivedTwo='" + derivedTwo + '\'' +
				"} " + super.toString();
	}
}
