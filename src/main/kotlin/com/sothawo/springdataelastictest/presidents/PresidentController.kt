package com.sothawo.springdataelastictest.presidents

import com.sothawo.springdataelastictest.ResourceNotFoundException
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.lang.Nullable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("presidents")
class PresidentController(
    private val repository: PresidentRepository,
    private val operations: ElasticsearchOperations,
    private val service: PresidentService,
) {

    @GetMapping("/load")
    fun load(): Iterable<President> = service.load()

    @GetMapping("/term/{year}")
    fun searchByTerm(@PathVariable year: Int): SearchHits<President> = service.searchByTerm(year)

    @GetMapping("/name/{name}")
    fun searchByName(@PathVariable name: String, @RequestParam(required = false) requestCache: Boolean?) = service.searchByname(name, requestCache)

    @GetMapping("/{id}")
    @Nullable
    fun byId(@PathVariable id: String) = service.byId(id) ?: throw ResourceNotFoundException()

}
