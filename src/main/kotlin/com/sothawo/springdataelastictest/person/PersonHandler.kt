package com.sothawo.springdataelastictest.person

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Component
class PersonHandler(private val  service: PersonService) {

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
