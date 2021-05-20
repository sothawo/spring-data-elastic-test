package com.sothawo.springdataelastictest.person

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Component
class PersonHandler(private val  service: PersonService) {

    fun create(request: ServerRequest): Mono<ServerResponse> {
        val count = request.pathVariable("count").toInt()
        return service.create(count).then(ServerResponse.ok().build())
    }

    fun all(request: ServerRequest?): Mono<ServerResponse> {
        return ServerResponse.ok().body(service.all(), Person::class.java)
    }

    fun allWithAge(request: ServerRequest?): Mono<ServerResponse> {
        return ServerResponse.ok().body(service.allWithAge(), Person::class.java)
    }
}
