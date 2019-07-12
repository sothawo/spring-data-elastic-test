package com.sothawo.springdataelastictest;

import reactor.core.publisher.Flux;

import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@Profile("reactive")
@RestController
@EnableReactiveElasticsearchRepositories
@RequestMapping("/repo")
public class ReactiveElasticsearchRepositoryController {

	private ReactivePersonRepository personRepository;

	public ReactiveElasticsearchRepositoryController(ReactivePersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@PostMapping("/person")
	public String savePerson(@RequestBody final Person person) {
		return personRepository.save(person).block().getId().toString();
	}

	@GetMapping("/persons")
	public Flux<Person> allPersons() {
		Flux<Person> all = personRepository.findAll();
		return all;
	}

	@GetMapping("/persons/{lastName}")
	public Flux<Person> byLastName(@PathVariable("lastName") final String lastName) {
		return personRepository.findByLastName(lastName);
	}

	@GetMapping("/person/{id}")
	public Person byId(@PathVariable("id") final Long id) {
		return personRepository.findById(id).blockOptional()
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}
}
