package com.sothawo.springdataelastictest.so;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.ValueConverter;
import org.springframework.data.elasticsearch.core.mapping.PropertyValueConverter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
	@Field(type = FieldType.Date, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSX||uuuu-MM-dd'T'HH:mm:ss.SSS", format = {})
	@ValueConverter(CustomZonedDateTimeConverter.class)
	private ZonedDateTime someDate;

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
}

class CustomZonedDateTimeConverter implements PropertyValueConverter {

	private final DateTimeFormatter formatterWithZone = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSX");
	private final DateTimeFormatter formatterWithoutZone = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSS");

	@Override
	public Object write(Object value) {
		if (value instanceof ZonedDateTime zonedDateTime) {
			return formatterWithZone.format(zonedDateTime);
		} else {
			return value;
		}
	}

	@Override
	public Object read(Object value) {
		if (value instanceof String s) {
			try {
				return formatterWithZone.parse(s, ZonedDateTime::from);
			} catch (Exception e) {
				return formatterWithoutZone.parse(s, LocalDateTime::from).atZone(ZoneId.of("UTC"));
			}
		} else {
			return value;
		}
	}
}
