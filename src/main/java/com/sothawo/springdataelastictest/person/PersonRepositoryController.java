package com.sothawo.springdataelastictest.person;

import com.github.javafaker.Faker;
import com.github.javafaker.Zelda;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/repo")
public class PersonRepositoryController {

    private static final Logger LOG = LoggerFactory.getLogger(PersonRepositoryController.class);

    private final PersonRepository personRepository;

    Faker faker = new Faker(Locale.GERMANY);

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
        person.setInternalId(id);
        person.setFirstName(faker.name().firstName());
        person.setLastName(faker.name().lastName());
        var birthday = faker.date().birthday();
        var instant = Instant.ofEpochMilli(birthday.getTime());
        var localDate = LocalDate.ofInstant(instant, ZoneId.of("UTC"));
        person.setBirthDate(localDate);
        return person;
    }

    @PostMapping("/person")
    public Person savePerson(@RequestBody final Person person) {
        return personRepository.save(person);
    }

    @GetMapping("/persons")
    public List<Person> allPersons() {
        Iterable<Person> all = personRepository.findAll(Pageable.unpaged());
//        final List<Person> all = personRepository.findAllByOrderByFirstName();
        return StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());
    }

    @GetMapping("/persons/{name}")
    public SearchHits<Person> byName(@PathVariable("name") final String name) {
        SearchHits<Person> searchHits = personRepository.queryByLastNameOrFirstNameOrderByBirthDate(name, name);
        LOG.debug("{}", searchHits);
        searchHits.forEach(searchHit -> LOG.debug("{}", searchHit));
        return searchHits;
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> byId(@PathVariable("id") final Long id) {
        LOG.info("check with exists: {}", personRepository.existsById(id));
        return personRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/persons/birthday")
    public Stream<SearchHit<Person>> afterMyBirthDay() {
        return personRepository.findByBirthDateAfter(LocalDate.of(1965, 6, 7));
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
    public Stream<Person> firstName(@PathVariable("name") String name) {
        return personRepository.queryWithFirstName(name);
    }

    @GetMapping("persons/lastname/{name}")
    public Stream<Person> lastName(@PathVariable("name") String name) {
        return personRepository.findByLastName(name);
    }

    @GetMapping("persons/firstNameWithLastNameCounts/{firstName}")
    public Pair<List<SearchHit<Person>>, Map<String, Long>> firstNameWithLastNameCounts(@PathVariable("firstName") String firstName) {

        // helper function to get the lastName counts from an Elasticsearch Aggregations
        Function<Aggregations, Map<String, Long>> getLastNameCounts = aggregations -> {
            if (aggregations != null) {
                Aggregation lastNames = aggregations.get("lastNames");
                if (lastNames != null) {
                    List<? extends Terms.Bucket> buckets = ((Terms) lastNames).getBuckets();
                    if (buckets != null) {
                        return buckets.stream().collect(Collectors.toMap(Terms.Bucket::getKeyAsString, Terms.Bucket::getDocCount));
                    }
                }
            }
            return Collections.emptyMap();
        };

        Map<String, Long> lastNameCounts = null;
        List<SearchHit<Person>> searchHits = new ArrayList<>();

        Pageable pageable = PageRequest.of(0, 1000);
        boolean fetchMore = true;
        while (fetchMore) {
            SearchPage<Person> searchPage = personRepository.findByFirstNameWithLastNameCounts(firstName, pageable);

            if (lastNameCounts == null) {
                Aggregations aggregations = searchPage.getSearchHits().getAggregations();
                lastNameCounts = getLastNameCounts.apply(aggregations);
            }

            if (searchPage.hasContent()) {
                searchHits.addAll(searchPage.getContent());
            }

            pageable = searchPage.nextPageable();
            fetchMore = searchPage.hasNext();
        }

        return Pair.of(searchHits, lastNameCounts);
    }

}
