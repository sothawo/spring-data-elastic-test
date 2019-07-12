package com.sothawo.springdataelastictest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@Profile("rest")
@EnableElasticsearchRepositories
@RestController
@RequestMapping("/repo")
public class ElasticsearchRepositoryController {

	private PersonRepository personRepository;

	public ElasticsearchRepositoryController(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@PostMapping("/person")
	public String savePerson(@RequestBody final Person person) {
		return personRepository.save(person).getId().toString();
	}

	@GetMapping("/persons")
	public List<Person> allPersons() {
		final Iterable<Person> all = personRepository.findAll();
		final List<Person> list = StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());
		return list;
	}

	@GetMapping("/persons/{lastName}")
	public Optional<Person> byLastName(@PathVariable("lastName") final String lastName) {
		return personRepository.findByLastNameFuzzy(lastName);
	}

	@GetMapping("/person/{id}")
	public Person byId(@PathVariable("id") final Long id) {
		return personRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND)
        );
	}
}
