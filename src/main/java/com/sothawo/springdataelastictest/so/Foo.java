package com.sothawo.springdataelastictest.so;

import com.sothawo.springdataelastictest.CustomZonedDateTimeConverter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.ValueConverter;
import org.springframework.lang.Nullable;

import java.time.ZonedDateTime;

@Document(indexName = "foo")
public class Foo {
	@Nullable
	@Id
	private String id;

	@Nullable
	@Field(type = FieldType.Text)
	private String text;

	@Nullable
	@Field(type = FieldType.Text)
	private String moreText;

	@Nullable
	@Field(name = "dotted.text", type = FieldType.Text)
	private String dottedText;

	@Nullable
	@Field(type = FieldType.Date, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSX||uuuu-MM-dd'T'HH:mm:ss.SSS", format = {})
	@ValueConverter(CustomZonedDateTimeConverter.class)
	private ZonedDateTime someDate;

	@Nullable
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
	public ZonedDateTime getSomeDate() {
		return someDate;
	}

	public void setSomeDate(@Nullable ZonedDateTime someDate) {
		this.someDate = someDate;
	}

	@Nullable
	public String getMoreText() {
		return moreText;
	}

	public void setMoreText(@Nullable String moreText) {
		this.moreText = moreText;
	}

	@Nullable
	public String getDottedText() {
		return dottedText;
	}

	public void setDottedText(@Nullable String dottedText) {
		this.dottedText = dottedText;
	}
}
