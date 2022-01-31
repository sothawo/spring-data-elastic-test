package com.sothawo.springdataelastictest.so;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

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
	@Field(type = FieldType.Date, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS", format = {})
	private LocalDateTime someDate;

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
	public LocalDateTime getSomeDate() {
		return someDate;
	}

	public void setSomeDate(@Nullable LocalDateTime someDate) {
		this.someDate = someDate;
	}

	@Nullable
	public String getMoreText() {
		return moreText;
	}

	public void setMoreText(@Nullable String moreText) {
		this.moreText = moreText;
	}
}
