package com.sothawo.springdataelastictest.so

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.*

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "so-test")
@Setting(settingPath = "/asciifolding.json")
class TopLevelObject(
    @Id
    val id: String,
    // ----------------------------------------------------------- THIS WORKS
    @MultiField(
        mainField = Field(type = FieldType.Text, analyzer = "ascii_folding"),
        otherFields = [
            InnerField(type = FieldType.Keyword, suffix = "drowyek")
        ]
    )
    val name: String,
    @Field(type = FieldType.Nested)
    val nestedObject: NestedObject
)

class NestedObject(
    val aField: String,
    // --------------------------------------------------------- THIS DOESN'T
    @MultiField(
        mainField = Field(type = FieldType.Text, analyzer = "ascii_folding"),
        otherFields = [
            InnerField(type = FieldType.Keyword, suffix = "drowyek")
        ]
    )
    val anotherField: String
)
