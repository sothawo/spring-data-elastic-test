package com.sothawo.springdataelastictest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/repo")
public class ElasticsearchRepositoryController {

    private PersonRepository personRepository;

    public ElasticsearchRepositoryController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/persons")
    public List<Person> allPersons() {
        final Iterable<Person> all = personRepository.findAll();
        final List<Person> list = StreamSupport.stream(
                all.spliterator(), false)
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/person/{lastName}")
    public Optional<Person> byLastName(@PathVariable("lastName") final String lastName) {
        return personRepository.findByLastName(lastName);
    }
}
