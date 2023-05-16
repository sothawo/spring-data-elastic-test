package com.sothawo.springdataelastictest.so

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "foo")
data class Foo(
	@Id
	val id: String?,
	@Version
	val version: Long?,
	@Field(type = FieldType.Text)
	val name: String?,
	)
