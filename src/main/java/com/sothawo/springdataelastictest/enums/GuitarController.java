package com.sothawo.springdataelastictest.enums;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/guitars")
public class GuitarController {

	private final GuitarRepository repository;

	public GuitarController(GuitarRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/test")
	void test() {
		var takamine = new Guitar("1", Manufacturer.TAKAMINE, 2000);
		var guild = new Guitar("2", Manufacturer.GUILD, 1992);
		var fender = new Guitar("3", Manufacturer.FENDER, 2006);
		var yamaha = new Guitar("4", Manufacturer.YAMAHA, 2005);
		var hoefner = new Guitar("5", Manufacturer.HOEFNER, 1977);

		repository.saveAll(List.of(takamine, guild, fender, yamaha, hoefner));

		repository.findAllByOrderByYearAsc().forEach(guitar -> {
			System.out.println(String.format("In %d I bought a guitar manufactured by %s in %s",
					guitar.year(), guitar.manufacturer().getDisplayName(), guitar.manufacturer().getCountry()));
		});
	}
}
