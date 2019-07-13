/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/network")
class NetworkRepositoryController(private val networkRepository: NetworkRepository) {

    @PostMapping("/networks")
    fun saveNetwork(@RequestBody network: Network): Network = networkRepository.save(network)

    @GetMapping("/networks")
    fun findAll(): List<Network> = networkRepository.findAll().toList()

    @GetMapping("/{ip}")
    fun findByIp(@PathVariable("ip") ip: String): List<Network> = networkRepository.findByCidr(ip)
}
