package com.sothawo.springdataelastictest.person

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
@Document(indexName = "person")
@Mapping(runtimeFieldsPath = "/runtime-fields-person.json")
data class Person(

    @Id
    @Nullable
    @Field(type = FieldType.Long, name = "id")
    val internalId: Long? = null,

    @Nullable
    @Version
    val version: Long? = null,

    @Field(type = FieldType.Text, fielddata = true)
    @Nullable
    val lastName: String? = null,

    @Field(type = FieldType.Text, fielddata = true)
    @Nullable
    val firstName: String? = null,

    @Field(type = FieldType.Date, format = [DateFormat.basic_date])
    @Nullable
    val birthDate: LocalDate? = null,

    @ReadOnlyProperty // do not write to prevent ES from automapping
    @Nullable
    val age: Int? = null,

    @Field(type = FieldType.Nested)
    @Nullable
    val cars: List<Car>? = null,

    @CreatedDate
    @Field(type = FieldType.Date, format = [DateFormat.basic_date_time])
    @Nullable
    val created: Instant? = null,

    @CreatedBy
    @Nullable
    val createdBy: String? = null,

    @LastModifiedDate
    @Field(type = FieldType.Date, format = [DateFormat.basic_date_time])
    @Nullable
    val lastModified: Instant? = null,

    @LastModifiedBy
    @Nullable
    val lastModifiedBy: String? = null,
) : Persistable<Long?> {

    @Nullable
    override fun getId(): Long? {
        return internalId
    }

    @JsonIgnore
    override fun isNew(): Boolean {
        return internalId == null || createdBy == null && created == null
    }

    companion object {

        private val FAKER: Faker = Faker(Locale.GERMANY)

        fun create(id: Long): Person = Person(
            internalId = id,
            firstName = FAKER.name().firstName(),
            lastName = FAKER.name().lastName(),
            birthDate = LocalDate.ofInstant(Instant.ofEpochMilli(FAKER.date().birthday().time), ZoneId.of("UTC")),
        )
    }
}
