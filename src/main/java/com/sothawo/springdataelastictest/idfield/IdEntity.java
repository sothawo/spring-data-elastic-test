package com.sothawo.springdataelastictest.idfield;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * An entity where the @Id property is not named id and where a second property is mapped to the name id in
 * Elasticsearch.
 */
@Document(indexName = "idfield")
public class IdEntity {

	@Id
	@ReadOnlyProperty
	private String realId;

	@Field(name = "id", type = FieldType.Keyword)
	private String fieldId;

	@Field(type = FieldType.Long)
	private Long whatever;

	public String getRealId() {
		return realId;
	}

	public void setRealId(String realId) {
		this.realId = realId;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public Long getWhatever() {
		return whatever;
	}

	public void setWhatever(Long whatever) {
		this.whatever = whatever;
	}

	@Override
	public String toString() {
		return "IdEntity{" +
			   "realId='" + realId + '\'' +
			   ", fieldId='" + fieldId + '\'' +
			   ", whatever=" + whatever +
			   '}';
	}
}
