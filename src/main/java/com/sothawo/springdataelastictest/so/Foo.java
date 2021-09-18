package com.sothawo.springdataelastictest.so;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document(indexName = "foo")
public class Foo {
	@Id
	@ReadOnlyProperty
	private String id;

	@Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ss")
	private LocalDateTime startTime;

//	@Field(type = FieldType.Nested)
	private Set<Bar> bars = new HashSet<>();

	@Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ss")
	private LocalDateTime endTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Set<Bar> getBars() {
		return bars;
	}

	public void setBars(Set<Bar> bars) {
		this.bars = bars;
	}

	public void addBar(Bar bar) {
		this.bars.add(bar);
	}

	@Override
	public String toString() {
		return "Foo{" +
			"id='" + id + '\'' +
			", startTime=" + startTime +
			", bars=" + bars +
			", endTime=" + endTime +
			'}';
	}

	public static class Bar {
		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "Bar{" +
				"value='" + value + '\'' +
				'}';
		}
	}
}
