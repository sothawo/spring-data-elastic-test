package com.sothawo.springdataelastictest.so

import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/foo")
class FooController(
		private val service: FooService
) {
		@GetMapping("/native")
		fun nativeQuery(): MutableList<SearchHit<Foo>> = service.nativeQuery().searchHits

		@GetMapping("/test")
		fun test() {
				service.test()
		}
}
