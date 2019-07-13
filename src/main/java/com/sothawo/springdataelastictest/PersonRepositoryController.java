package com.sothawo.springdataelastictest;

import com.devskiller.jfairy.Fairy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/repo")
public class PersonRepositoryController {

    private static final Logger LOG = LoggerFactory.getLogger(PersonRepositoryController.class);

    private PersonRepository personRepository;

    Fairy fairy = Fairy.create(Locale.ENGLISH);

    public PersonRepositoryController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/persons/create/{count}")
    public void create(@PathVariable("count") Long count) {

        personRepository.deleteAll();


        long maxId = count;
        long fromId = 1L;

        while (fromId < maxId) {
            long toId = Math.min(fromId + 1000, maxId);

            List<Person> persons = LongStream.range(fromId, toId + 1)
                .mapToObj(this::createPerson)
                .collect(Collectors.toList());

            personRepository.saveAll(persons);

            fromId += 1000L;
        }
    }

    private Person createPerson(long id) {
        Person person = new Person();
        com.devskiller.jfairy.producer.person.Person fairyPerson = fairy.person();
        person.setId(id);
        person.setFirstName(fairyPerson.getFirstName());
        person.setLastName(fairyPerson.getLastName());
        person.setBirthDate(fairyPerson.getDateOfBirth());
        return person;
    }

    @PostMapping("/person")
    public String savePerson(@RequestBody final Person person) {
        return personRepository.save(person).getId().toString();
    }

    @GetMapping("/persons")
    public List<Person> allPersons() {
        Iterable<Person> all = personRepository.findAll(Pageable.unpaged());
//        final List<Person> all = personRepository.findAllByOrderByFirstName();
        final List<Person> list = StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());
        return list;
    }

    @GetMapping("/persons/{name}")
    public SearchHits<Person> byName(@PathVariable("name") final String name) {
        SearchHits<Person> searchHits = personRepository.queryByLastNameOrFirstNameOrderByBirthDate(name, name);
        LOG.debug("{}", searchHits);
        searchHits.forEach(searchHit -> LOG.debug("{}", searchHit));
        return searchHits;
    }

    @GetMapping("/person/{id}")
    public Person byId(@PathVariable("id") final Long id) {
        LOG.info("check with exists: {}", personRepository.existsById(id));
        return personRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/persons/birthday")
    public Stream<SearchHit<Person>> afterMyBirthDay() {
        return personRepository.findByBirthDateAfter(LocalDate.of(1965, 6, 7));
    }

    @GetMapping("/person/custom")
    public Person custom() {
        Person person = createPerson(500_000);
        return personRepository.saveNoRefresh(person);
    }

    @GetMapping("/persons/count")
    public long count() {
        return personRepository.count();
    }

    @GetMapping("/personsPaged/{name}")
    public List<SearchHit<Person>> personsPaged(@PathVariable("name") String name) {
        List<SearchHit<Person>> persons = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 42);
        while (pageable != Pageable.unpaged()) {
            SearchPage<Person> searchPage = personRepository.searchByLastName(name, pageable);
            persons.addAll(searchPage.getContent());
            LOG.info("got {} of {} records, now at {}", searchPage.getContent().size(), searchPage.getTotalElements(), persons.size());
            pageable = searchPage.nextPageable();
        }
        return persons;
    }

    @GetMapping("persons/firstname/{name}")
    public SearchHits<Person> firstName(@PathVariable("name") String name) {
        SearchHits<Person> searchHits = personRepository.queryWithFirstName(name);
        return searchHits;
    }
}
