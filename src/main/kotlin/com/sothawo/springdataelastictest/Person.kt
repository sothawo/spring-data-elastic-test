/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "person")
class Person {
    @Id
    var id: Long? = null

    @Field(value = "last-name", type = FieldType.Text)
    var lastName: String? = null

    @Field(name = "first-name", type = FieldType.Text, fielddata = true)
    var firstName: String? = null

    @Field(type = FieldType.Nested)
    private val movies: List<Movie>? = null

    override fun toString(): String {
        return "Person{" +
                "id=" + id +
                ", lastName='" + lastName + '\''.toString() +
                ", firstName='" + firstName + '\''.toString() +
                '}'.toString()
    }
}
