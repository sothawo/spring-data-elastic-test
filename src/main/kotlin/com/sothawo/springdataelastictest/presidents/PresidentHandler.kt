package com.sothawo.springdataelastictest.presidents

import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.queryParamOrNull
import reactor.core.publisher.Mono
import kotlin.let

@Component
class PresidentHandler(
    private val service: PresidentService
) {
    fun load(request: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok().body(service.load(), President::class.java)

    fun byId(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id")
        return ServerResponse.ok().body(service.byId(id), President::class.java)
    }

    fun searchByTerm(request: ServerRequest): Mono<ServerResponse> {
        val year = request.pathVariable("year").toInt()
        return ServerResponse.ok().body(service.searchByTerm(year), SearchHit::class.java)
    }

    fun searchByName(request: ServerRequest): Mono<ServerResponse> {
        val name = request.pathVariable("name")
        val requestCache = request.queryParamOrNull("requestCache")?.let { it -> it.toBoolean() }

        return ServerResponse.ok()
            .body(service.searchByName(name, requestCache), SearchHit::class.java)
    }
}
