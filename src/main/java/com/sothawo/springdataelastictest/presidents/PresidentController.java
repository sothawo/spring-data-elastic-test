/*
 * (c) Copyright 2020 codecentric AG
 */
package com.sothawo.springdataelastictest.presidents;

import com.sothawo.springdataelastictest.ResourceNotFoundException;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("presidents")
public class PresidentController {

    private final PresidentService service;

    public PresidentController(PresidentService service) {
        this.service = service;
    }

    @GetMapping("/load")
    public Iterable<President> load() {
        return service.load();
    }

    @GetMapping("/{id}")
    @Nullable
    public President byId(@PathVariable String id) {

        var president = service.byId(id);
        if (president == null) {
            throw new ResourceNotFoundException();
        }
        return president;
    }

    @GetMapping("/term/{year}")
    public SearchHits<President> searchByTerm(@PathVariable Integer year) {
        return service.searchByTerm(year);
    }

    @GetMapping("/name/{name}")
    public SearchHits<President> searchByName(@PathVariable String name, @Nullable @RequestParam(required = false) Boolean requestCache) {
        return service.searchByName(name, requestCache);
    }
}
