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
package com.sothawo.springdataelastictest.italy;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.sothawo.springdataelastictest.italy.Italy.City;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.InnerHitBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/italy")
public class ItalyController {

    private final ItalyRepository repository;
    private final ElasticsearchOperations operations;

    public ItalyController(ItalyRepository repository, ElasticsearchOperations operations) {
        this.repository = repository;
        this.operations = operations;
    }

    @GetMapping("/load")
    public void load() {
        repository.deleteAll();

        Faker faker = new Faker(Locale.ITALY);

        Map<String, City> cities = new LinkedHashMap<>();

        for (int i = 0; i < 100; i++) {

            Address address = faker.address();
            String cityName = address.cityName();

            City city = cities.computeIfAbsent(cityName, City::new);

            int number = Integer.parseInt(address.streetAddressNumber());
            number %= 50;
            number++;
            Italy.House houseKey = new Italy.House(address.streetName(), String.valueOf(number));

            Italy.House house = city.getHousesMap().computeIfAbsent(houseKey, Function.identity());
            house.getInhabitants().add(new Italy.Inhabitant(faker.name().firstName(), faker.name().lastName()));
        }

        cities.values().forEach(City::close);

        repository.saveAll(cities.values());
    }

    @GetMapping("/firstName/{firstName}")
    public SearchHits<City> searchByFirstName(@PathVariable String firstName) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        NestedQueryBuilder nestedQueryBuilder = nestedQuery("houses.inhabitants",
            matchQuery("houses.inhabitants.firstName", firstName),
            ScoreMode.Avg);
        nestedQueryBuilder.innerHit(new InnerHitBuilder());
        queryBuilder.withQuery(nestedQueryBuilder);

        NativeSearchQuery query = queryBuilder.build();
        return operations.search(query, City.class);
    }
}
