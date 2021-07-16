package com.sothawo.springdataelastictest.population

import com.fasterxml.jackson.annotation.JsonIgnore
import com.github.javafaker.Faker
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.ReadOnlyProperty
import org.springframework.data.annotation.Version
import org.springframework.data.domain.Persistable
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.data.elasticsearch.annotations.Mapping
import org.springframework.data.elasticsearch.annotations.ScriptedField
import org.springframework.lang.Nullable
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Locale

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "population-person")
data class Person(

    @Id
    var id: String? = null,

    @Field(type = FieldType.Text)
    var lastName: String? = null,

    @Field(type = FieldType.Text)
    var firstName: String? = null,

    @Field(type = FieldType.Keyword)
    var house: String? = null
) {
    companion object {

        private val FAKER: Faker = Faker(Locale.GERMANY)

        fun create(id: String): Person = Person(
            id = id,
            firstName = FAKER.name().firstName(),
            lastName = FAKER.name().lastName(),
        )
    }
}
