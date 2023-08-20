package com.sothawo.springdataelastictest.products

import com.sothawo.springdataelastictest.ResourceNotFoundException
import org.springframework.data.elasticsearch.client.elc.NativeQuery
import org.springframework.data.elasticsearch.client.elc.Queries.idsQueryAsQuery
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter
import org.springframework.data.elasticsearch.core.query.ScriptData
import org.springframework.data.elasticsearch.core.query.ScriptType
import org.springframework.data.elasticsearch.core.query.ScriptedField
import org.springframework.web.bind.annotation.*

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("products")
class ProductController(
		private val repository: ProductRepository,
		private val operations: ElasticsearchOperations
) {
		@PostMapping
		fun save(@RequestBody product: Product) = repository.save(product)

		@GetMapping("/{id}")
		fun getById(@PathVariable id: String): Product {

				val query = NativeQuery.builder()
						.withQuery(idsQueryAsQuery(listOf(id)))
						.withSourceFilter(FetchSourceFilter(arrayOf("*"), emptyArray()))
						.withScriptedField(
								ScriptedField(
										"gross-price",
										ScriptData(ScriptType.INLINE, "expression", "doc['net-price'] * factor", "", mapOf("factor" to 1.2))
								)
						)
						.build()

				val searchHits = operations.search(query, Product::class.java)

				return if (searchHits.totalHits > 0) searchHits.getSearchHit(0).content else throw ResourceNotFoundException()
		}
}
