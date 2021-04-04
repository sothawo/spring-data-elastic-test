package com.sothawo.springdataelastictest;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/repo")
public class ReactiveElasticsearchRepositoryController {

    private ReactivePersonRepository personRepository;

    public ReactiveElasticsearchRepositoryController(ReactivePersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PostMapping("/person")
    public Mono<Person> savePerson(@RequestBody final Person person) {
        return personRepository.save(person);
    }

    @PostMapping("/persons")
    public Flux<Person> savePersons(@RequestBody Flux<Person> persons) {
        return personRepository.saveAll(persons);
    }

    @GetMapping("/persons")
    public Flux<Person> allPersons() {
        Flux<Person> all = personRepository.findAll();
        return all;
    }

    @GetMapping("/persons/{name}")
    public Flux<SearchHit<Person>> byLastName(@PathVariable("name") final String name) {
        return personRepository.findTop10ByLastNameOrFirstName(name, name);
    }

    @GetMapping("/persons/firstname/{name}")
    public Flux<SearchHit<Person>> byFirstName(@PathVariable("name") String name) {
        return personRepository.queryWithFirstName(name);
    }

    @GetMapping("/person/{id}")
    public Mono<Person> byId(@PathVariable("id") final Long id) {
        return personRepository.findById(id).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "not found")));
    }

}