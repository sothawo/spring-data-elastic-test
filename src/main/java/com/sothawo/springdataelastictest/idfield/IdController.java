package com.sothawo.springdataelastictest.idfield;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/identities")
public class IdController {

	private static final Logger LOG = LoggerFactory.getLogger(IdController.class);

	private final IdRepository repository;

	public IdController(IdRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/test")
	public void test() {
		var idEntity = new IdEntity();
		idEntity.setRealId("real");
		idEntity.setFieldId("field");
		idEntity.setWhatever(42L);
		repository.save(idEntity);

		var foundEntity = repository.findById("real");

		LOG.info(foundEntity.toString());
	}
}
