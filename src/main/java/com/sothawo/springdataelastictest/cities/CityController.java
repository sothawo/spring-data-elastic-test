/*
 Copyright 2020 the original author(s)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */
package com.sothawo.springdataelastictest.cities;

import com.sothawo.springdataelastictest.fakers.FakeAddress;
import com.sothawo.springdataelastictest.fakers.FakeCity;
import com.sothawo.springdataelastictest.fakers.FakePerson;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/cities")
public class CityController {

	private final CityRepository repository;
	private final ReactiveElasticsearchOperations operations;

	public CityController(CityRepository repository, ReactiveElasticsearchOperations operations) {
		this.repository = repository;
		this.operations = operations;
	}

	@GetMapping("/load")
	public Flux<City> load() {
		repository.deleteAll();

		Map<String, City> cities = new LinkedHashMap<>();

		for (int i = 0; i < 50; i++) {

			City city = cities.computeIfAbsent(FakeCity.city().getName(), City::new);

			for (int j = 0; j < 10; j++) {

				var fakeAddress = FakeAddress.address();

				int number = Integer.parseInt(fakeAddress.getHouseNumber());
				number %= 50;
				number++;
				House houseKey = new House(fakeAddress.getStreetName(), String.valueOf(number));
				House house = city.getHousesMap().computeIfAbsent(houseKey, Function.identity());

				for (int k = 0; k < 5; k++) {
					var fakePerson = FakePerson.person();
					house.getInhabitants().add(new Inhabitant(fakePerson.getFirstName(), fakePerson.getLastName()));
				}
			}
		}

		cities.values().forEach(City::close);
		return repository.saveAll(cities.values());
	}

	@GetMapping("/firstName/{firstName}")
	public Flux<SearchHit<City>> searchByFirstName(@PathVariable String firstName) {
		return operations.search(new CriteriaQuery(new Criteria("houses.inhabitants.firstName").is(firstName)),
				City.class);
	}

	@GetMapping("/street-keyword/{keyword}")
	public Flux<SearchHit<City>> searchByStreetKeyword(@PathVariable String keyword) {
		return operations.search(new CriteriaQuery(new Criteria("houses.street.keyword").is(keyword)), City.class);
	}


}
