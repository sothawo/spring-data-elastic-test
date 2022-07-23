/*
 * (c) Copyright 2022 sothawo
 */
package com.sothawo.springdataelastictest.compositeid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/composite")
public class CompositeController {

	private final CompositeRepository repository;

	public CompositeController(CompositeRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/save")
	public CompositeEntity save() {
		var entity = getCompositeEntity();
		return repository.save(entity);
	}

	private CompositeEntity getCompositeEntity() {
		var entity = new CompositeEntity();
//		CompositeId compositeId = getId();
		entity.setDocumentId("id42");
		entity.setName("name42");
		entity.setText("some text");
		return entity;
	}

	@GetMapping("/get")
	public Optional<CompositeEntity> get() {
		return repository.findById(getCompositeEntity().getElasticsearchId());
	}

	private CompositeId getId() {
		CompositeId compositeId = new CompositeId();
		compositeId.setId(42L);
		compositeId.setName("name42");
		return compositeId;
	}
}
