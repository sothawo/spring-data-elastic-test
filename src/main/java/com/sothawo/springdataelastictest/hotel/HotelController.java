package com.sothawo.springdataelastictest.hotel;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/hotels")
public class HotelController {

    private final HotelRepository repository;

    public HotelController(HotelRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    public SearchHits<Hotel> all() {
        return repository.searchAllBy();
    }

    @PostMapping()
    public Hotel save(@RequestBody Hotel hotel) {
        return repository.save(hotel);
    }

    @GetMapping("/id1/{id}")
    public Hotel findById(@PathVariable("id") String id) {
        return repository.findById(id).orElse(null);
    }

    @GetMapping("/id2/{id}")
    public SearchHits<Hotel> searchById(@PathVariable("id") String id) {
        return repository.searchById(id);
    }
}
