/*
 * (c) Copyright 2022 sothawo
 */
package com.sothawo.springdataelastictest.so;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

import java.util.UUID;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "location-search")
public class LocationSearch {
	@Id
	private String id = UUID.randomUUID().toString();

	@MultiField(
		mainField = @Field(type = FieldType.Text),
		otherFields = {@InnerField(suffix = "keyword", type = FieldType.Keyword)}
	)
	private String locationId;

	@MultiField(
		mainField = @Field(type = FieldType.Text),
		otherFields = {@InnerField(suffix = "keyword", type = FieldType.Keyword)}
	)
	private String name;

	@MultiField(
		mainField = @Field(type = FieldType.Text),
		otherFields = {@InnerField(suffix = "keyword", type = FieldType.Keyword)}
	)
	private String code;

//	@Field(type=FieldType.Nested, name="parentLocation")
//	private ParentLocationSearch parentLocation;

	@MultiField(
		mainField = @Field(type = FieldType.Text),
		otherFields = {@InnerField(suffix = "keyword", type = FieldType.Keyword)}
	)
	private String tenantId;

	@Field(type = FieldType.Nested, name = "locationType")
	private LocationTypeSearch locationType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public LocationTypeSearch getLocationType() {
		return locationType;
	}

	public void setLocationType(LocationTypeSearch locationType) {
		this.locationType = locationType;
	}
}
