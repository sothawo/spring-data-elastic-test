/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest.network;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/network")
public class NetworkRepositoryController {
	private final NetworkRepository networkRepository;

	public NetworkRepositoryController(NetworkRepository networkRepository) {
		this.networkRepository = networkRepository;
	}

	@PostMapping("/networks")
	public Network saveNetwork(@RequestBody Network network) {
		return networkRepository.save(network);
	}

	@GetMapping("/networks")
	public List<Network> findAll() {
		return StreamSupport.stream(networkRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@GetMapping("/{ip}")
	public List<Network> findByIp(@PathVariable("ip") String ip) {
		return networkRepository.findByCidr(ip);
	}
}
