package com.sothawo.springdataelastictest.presidents

import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import kotlin.let

@Component
class PresidentHandler(
    private val service: PresidentService
) {
    suspend fun load(request: ServerRequest): ServerResponse =
        ok().json().bodyAndAwait(service.load())

    suspend fun byId(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id")
        return ok().json().bodyValueAndAwait(service.byId(id))
    }

    suspend fun searchByTerm(request: ServerRequest): ServerResponse {
        val year = request.pathVariable("year").toInt()
        return ok().json().json().bodyAndAwait(service.searchByTerm(year))
    }

    suspend fun searchByName(request: ServerRequest): ServerResponse {
        val name = request.pathVariable("name")
        val requestCache = request.queryParamOrNull("requestCache")?.let { it -> it.toBoolean() }

        return ok().json().bodyAndAwait(service.searchByName(name, requestCache))
    }
}
