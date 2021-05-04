package com.sothawo.springdataelastictest.presidents

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.util.UUID

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "presidents")
data class President @PersistenceConstructor constructor(
    @Id
    val id: String?,
    @Field(type = FieldType.Text)
    val name: String?,
    @Field(type = FieldType.Integer_Range)
    val term: Term?
) {
    constructor(name: String?, term: Term?) : this(UUID.randomUUID().toString(), name, term)

    companion object {
        fun of(name: String?, from: Int?, to: Int?): President {
            return President(name, Term(from!!, to!!))
        }
    }
}
