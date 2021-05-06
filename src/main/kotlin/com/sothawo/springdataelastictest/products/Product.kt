package com.sothawo.springdataelastictest.products

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.data.elasticsearch.annotations.ScriptedField

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "products")
data class Product(
    @Id
    val id: String?,
    @Field(type = FieldType.Text)
    val name: String?,
    @Field(type = FieldType.Double)
    val netPrice: Double?,
    @ScriptedField
    val grossPrice: Double?
)
