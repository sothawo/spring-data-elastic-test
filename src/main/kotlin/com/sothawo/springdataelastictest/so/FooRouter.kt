package com.sothawo.springdataelastictest.so

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration("FooRouterConfiguration")
class FooRouter {

	fun fooRouter(handler: FooHandler) = coRouter {
		"/foo".nest {
			GET("/test", handler::test)
		}
	}
}
