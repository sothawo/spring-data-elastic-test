package com.sothawo.springdataelastictest.scriptandruntime;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sar")
public class SaRPersonController {

    private final SaRPersonService service;

    public SaRPersonController(SaRPersonService service) {
        this.service = service;
    }

    @PutMapping("/load")
    void load() {
        List<Person> persons = List.of(new Person("1", "Smith", "Mary", "f", "1987-05-03"),
                new Person("2", "Smith", "Joshua", "m", "1982-11-17"),
                new Person("3", "Smith", "Joanna", "f", "2018-03-27"),
                new Person("4", "Smith", "Alex", "m", "2020-08-01"),
                new Person("5", "McNeill", "Fiona", "f", "1989-04-07"),
                new Person("6", "McNeill", "Michael", "m", "1984-10-20"),
                new Person("7", "McNeill", "Geraldine", "f", "2020-03-02"),
                new Person("8", "McNeill", "Patrick", "m", "2022-07-04"));
        service.save(persons);
    }

    @GetMapping
    SearchHits<Person> all() {
        return service.findAllWithAge();
    }

    @GetMapping("/{gender}/{maxAge}")
    public SearchHits<Person> withGenderAndMaxAge(@PathVariable String gender, @PathVariable Integer maxAge) {
        return service.findWithGenderAndMaxAge(gender, maxAge);
    }
}
