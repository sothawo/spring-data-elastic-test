package com.sothawo.springdataelastictest.so

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "foo", storeIdInSource = false)
data class Foo(
		@Id
		@Field(type =  FieldType.Keyword)
		private val id: String?,

		@Field(type = FieldType.Text)
		private val text: String?,
)
