package com.sothawo.springdataelastictest.so

import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations
import org.springframework.stereotype.Service

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Service
class FooService(
		private val repository: FooRepository,
		private val operations: ReactiveElasticsearchOperations
) {

		suspend fun test(): Foo {
				val foo = Foo("42", 1, "foo")
				return repository.save(foo)
		}
}
