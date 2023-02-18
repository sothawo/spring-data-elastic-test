package com.sothawo.springdataelastictest.so

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.json

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Component
class FooHandler(private val service: FooService) {
	suspend fun test(request: ServerRequest): ServerResponse =
		ServerResponse.ok().json().bodyValueAndAwait(service.test())
}
