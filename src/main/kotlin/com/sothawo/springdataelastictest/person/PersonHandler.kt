package com.sothawo.springdataelastictest.person

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import org.springframework.web.reactive.function.server.json

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Component
class PersonHandler(private val service: PersonService) {

    suspend fun create(request: ServerRequest): ServerResponse {
        val count = request.pathVariable("count").toLong()
        service.create(count)
        return ok().buildAndAwait()
    }

    suspend fun all(request: ServerRequest?): ServerResponse {
        return ok().json().bodyAndAwait(service.all())
    }

    suspend fun allWithAge(request: ServerRequest?): ServerResponse {
        return ok().bodyAndAwait(service.allWithAge())
    }

    suspend fun byLastName(request: ServerRequest): ServerResponse {
        val lastName = request.pathVariable("lastName")
        val searchHits = service.byLastName(lastName)
        return ok().json().bodyValueAndAwait(searchHits)
    }
}
