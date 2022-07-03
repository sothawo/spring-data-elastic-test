/*
 * (c) Copyright 2022 sothawo
 */
package com.sothawo.springdataelastictest.range;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Range;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "vacations")
public class Vacation {
	@Id
	private String id;

	@Field(type=FieldType.Text) private String description;

	@Field(type = FieldType.Date_Range, format = DateFormat.date)
	Range<LocalDate> date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Range<LocalDate> getDate() {
		return date;
	}

	public void setDate(Range<LocalDate> date) {
		this.date = date;
	}

	public static Vacation of(String description, LocalDate from, LocalDate to) {
		var vacation = new Vacation();
		vacation.setId(UUID.randomUUID().toString());
		vacation.setDescription(description);
		vacation.setDate(Range.of(Range.Bound.inclusive(from), Range.Bound.inclusive(to)));
		return vacation;
	}
}
