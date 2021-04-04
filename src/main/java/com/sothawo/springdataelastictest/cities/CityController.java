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

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityRepository repository;
    private final ElasticsearchOperations operations;

    public CityController(CityRepository repository, ElasticsearchOperations operations) {
        this.repository = repository;
        this.operations = operations;
    }

    @GetMapping("/load")
    public void load() {
        repository.deleteAll();

        Faker faker = new Faker(Locale.ENGLISH);

        Map<String, City> cities = new LinkedHashMap<>();

        for (int i = 0; i < 50; i++) {

            String cityName = faker.address().cityName();
            City city = cities.computeIfAbsent(cityName, City::new);

            for (int j = 0; j < 10; j++) {

                Address address = faker.address();

                int number = Integer.parseInt(address.streetAddressNumber());
                number %= 50;
                number++;
                House houseKey = new House(address.streetName(), String.valueOf(number));
                House house = city.getHousesMap().computeIfAbsent(houseKey, Function.identity());

                for (int k = 0; k < 5; k++) {
                    house.getInhabitants().add(new Inhabitant(faker.name().firstName(), faker.name().lastName()));
                }
            }
        }

        cities.values().forEach(City::close);
        repository.saveAll(cities.values());
    }

    @GetMapping("/firstName/{firstName}")
    public SearchHits<City> searchByFirstName(@PathVariable String firstName) {
        return operations.search(new CriteriaQuery(new Criteria("houses.inhabitants.firstName").is(firstName)), City.class);
    }
}
