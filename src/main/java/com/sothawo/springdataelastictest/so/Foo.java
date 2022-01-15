package com.sothawo.springdataelastictest.so;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Document(indexName = "foo")
public class Foo {
	@Id
	private String id;

	@Nullable
	@Field(type = FieldType.Text)
	private String text;

	@Nullable
	@Field(type = FieldType.Text)
	private String moreText;

	@Nullable
	@Field(type = FieldType.Date, format = {DateFormat.basic_date})
	private LocalDate someData;

	@Nullable
	@Field(type = FieldType.Date, pattern = "uuuu-MM-dd", format = {})
	private LocalDate joinedDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Nullable
	public String getText() {
		return text;
	}

	public void setText(@Nullable String text) {
		this.text = text;
	}

	@Nullable
	public LocalDate getSomeData() {
		return someData;
	}

	public void setSomeData(@Nullable LocalDate someData) {
		this.someData = someData;
	}

	@Nullable
	public String getMoreText() {
		return moreText;
	}

	public void setMoreText(@Nullable String moreText) {
		this.moreText = moreText;
	}

	@Nullable
	public LocalDate getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(@Nullable LocalDate joinedDate) {
		this.joinedDate = joinedDate;
	}
}
