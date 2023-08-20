package com.sothawo.springdataelastictest.so

import org.springframework.data.elasticsearch.client.elc.NativeQuery
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.stereotype.Service

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Service
class FooService(
		private val operations: ElasticsearchOperations
) {
		fun nativeQuery(): SearchHits<Foo> {
val query = NativeQuery.builder().withQuery { q1 ->
		q1.bool { b ->
				listOf(1L, 2L).forEach { t ->
						b.filter { q2 ->
								q2.term { tq ->
										tq.field("tags.id").value(t)
										tq
								}
						}
				}
				b
		}
}.build()
				return operations.search(query, Foo::class.java)
		}
}
