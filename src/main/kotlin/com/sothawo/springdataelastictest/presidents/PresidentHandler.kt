package com.sothawo.springdataelastictest.presidents

import com.sothawo.springdataelastictest.ResourceNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.Boolean
import kotlin.Long
import kotlin.String
import kotlin.let

@Component
class PresidentHandler(
    private val repository: PresidentRepository,
    private val operations: ReactiveElasticsearchOperations
) {
    fun load(request: ServerRequest): Mono<ServerResponse> {
        val presidents: Flux<President> = repository.deleteAll()
            .thenMany(
                repository.saveAll(
                    listOf(
                        President.of("Dwight D Eisenhower", 1953, 1961),
                        President.of("Lyndon B Johnson", 1963, 1969),
                        President.of("Richard Nixon", 1969, 1974),
                        President.of("Gerald Ford", 1974, 1977),
                        President.of("Jimmy Carter", 1977, 1981),
                        President.of("Ronald Reagen", 1981, 1989),
                        President.of("George Bush", 1989, 1993),
                        President.of("Bill Clinton", 1993, 2001),
                        President.of("George W Bush", 2001, 2009),
                        President.of("Barack Obama", 2009, 2017),
                        President.of("Donald Trump", 2017, 2021),
                        President.of("Joe Biden", 2021, 2025)
                    )
                )
            )
        return ServerResponse.ok().body(presidents, President::class.java)
    }

    fun byId(request: ServerRequest) =
        ServerResponse.ok()
            .body(
                repository.findById(request.pathVariable("id"))
                    .switchIfEmpty(Mono.error(ResourceNotFoundException())), President::class.java
            )

    fun searchByTerm(request: ServerRequest) =
        ServerResponse.ok().body(repository.searchByTerm(request.pathVariable("year").toInt()), SearchHit::class.java)

    fun searchByName(request: ServerRequest): Mono<ServerResponse> {
        val name = request.pathVariable("name")
        val query = CriteriaQuery(Criteria("name").`is`(name))
        request.queryParam("requestCache")
            .map { s: String? -> Boolean.parseBoolean(s) }
            .orElse(null)?.let {
                query.requestCache = it
            }

        return ServerResponse.ok()
            .body(
                operations.count(query, President::class.java)
                    .flatMapMany<SearchHit<President>?> { count: Long? ->
                        LOGGER.info("#presidents: {}", count)
                        operations.search(query, President::class.java)
                    }, SearchHit::class.java
            )
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(PresidentHandler::class.java)
    }
}
