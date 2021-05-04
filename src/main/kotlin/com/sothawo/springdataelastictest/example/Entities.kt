package com.sothawo.springdataelastictest.example

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.*

@Document(indexName = "examples")
data class Example(
    @Id val id: String? = null,
    val name: String,
    @Field(type = FieldType.Nested)
    val inner: List<ExampleInner>,
    @Field(type = FieldType.Date, format = [DateFormat.date])
    val date: LocalDate
)

data class ExampleInner(
    val id: UUID,
    val name: String,
    @Field(type = FieldType.Date, format = [DateFormat.date])
    val date: LocalDate
)

interface ExampleRepository : ElasticsearchRepository<Example, String> {
    fun findAllByNameLike(name: String): SearchHits<Example>
}

@RestController
@RequestMapping("/example")
class ExampleController(private val repository: ExampleRepository) {

    @GetMapping
    fun example(): SearchHits<Example> {
        val example = Example(
            id = "42",
            name = "example",
            inner = listOf(
                ExampleInner(
                    id = UUID.randomUUID(),
                    name = "inner",
                    date = LocalDate.now().minusDays(1)
                )
            ),
            date = LocalDate.now(
            )
        )

        repository.save(example)
        return repository.findAllByNameLike("example")
    }
}
