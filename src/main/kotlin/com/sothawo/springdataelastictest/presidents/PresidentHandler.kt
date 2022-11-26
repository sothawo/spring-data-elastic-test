package com.sothawo.springdataelastictest.presidents

import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono
import kotlin.let

@Component
class PresidentHandler(
    private val service: PresidentService
) {
    suspend fun load(request: ServerRequest): ServerResponse =
        ServerResponse.ok().bodyAndAwait(service.load())

    suspend fun byId(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id")
        return ServerResponse.ok().bodyValueAndAwait(service.byId(id))
    }

    suspend fun searchByTerm(request: ServerRequest): ServerResponse {
        val year = request.pathVariable("year").toInt()
        return ServerResponse.ok().json().bodyAndAwait(service.searchByTerm(year))
    }

    suspend fun searchByName(request: ServerRequest): ServerResponse {
        val name = request.pathVariable("name")
        val requestCache = request.queryParamOrNull("requestCache")?.let { it -> it.toBoolean() }

        return ServerResponse.ok().bodyAndAwait(service.searchByName(name, requestCache))
    }
}
