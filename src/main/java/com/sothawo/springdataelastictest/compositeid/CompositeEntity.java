/*
 * (c) Copyright 2022 sothawo
 */
package com.sothawo.springdataelastictest.compositeid;

import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "composite-entity")
public class CompositeEntity {

	@Field(name = "id", type = FieldType.Keyword)
	private String documentId;

	@Field(type = FieldType.Keyword)
	private String name;

	@Field(type = FieldType.Text)
	private String text;

	@Id
	@AccessType(AccessType.Type.PROPERTY)
	public String getElasticsearchId() {
		return documentId + '-' + name;
	}

	public void setElasticsearchId(String ignored) {
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
