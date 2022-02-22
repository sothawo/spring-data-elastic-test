/*
 * (c) Copyright 2022 sothawo
 */
package com.sothawo.springdataelastictest.range;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/vacations")
public class VacationController {

	private final VacationRepository repository;

	public VacationController(VacationRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/load")
	public Iterable<Vacation> load() {
		return repository.saveAll(Arrays.asList(
			Vacation.of("Ostern 2020", LocalDate.of(2022, 4, 15), LocalDate.of(2022, 4, 23)),
			Vacation.of("Pfingsten 2020", LocalDate.of(2022, 6, 6), LocalDate.of(2022, 6, 18)),
			Vacation.of("Sommer 2020", LocalDate.of(2022, 7, 28), LocalDate.of(2022, 9, 10)),
			Vacation.of("Herbst 2020", LocalDate.of(2022, 11, 2), LocalDate.of(2022, 11, 4)),
			Vacation.of("Weihnachten 2020", LocalDate.of(2022, 12, 21), LocalDate.of(2023, 1, 7))
		));
	}

	@GetMapping("/search/{date}")
	public SearchHits<Vacation> search(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		return
			repository.searchByDate(date);
	}
}
