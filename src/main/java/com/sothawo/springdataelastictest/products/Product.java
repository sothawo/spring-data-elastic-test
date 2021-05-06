package com.sothawo.springdataelastictest.products;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.ScriptedField;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "products")
public record Product(
    @Id
    String id,
    @Field(type = FieldType.Text)
    String name,
    @Field(type = FieldType.Double)
    Double netPrice,
    @ScriptedField
    Double grossPrice
) {
}
