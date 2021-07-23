package com.sothawo.springdataelastictest.population

import com.github.javafaker.Faker
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.lang.Nullable
import java.util.Locale

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "population-house")
data class House(

    @Id
    var id: String? = null,

    @Field(type = FieldType.Keyword)
    var zip: String? = null,

    @Field(type = FieldType.Text)
    var city: String? = null,

    @Field(type = FieldType.Text)
    var street: String? = null,

    @Field(type = FieldType.Text)
    var streetNumber: String? = null,

    ) {
    companion object {

        private val FAKER: Faker = Faker(Locale.ENGLISH)

        fun create(id: String): House = House(
            id = id,
            FAKER.address().zipCode(),
            FAKER.address().cityName(),
            FAKER.address().streetName(),
            FAKER.address().streetAddressNumber(),
        )
    }
}
