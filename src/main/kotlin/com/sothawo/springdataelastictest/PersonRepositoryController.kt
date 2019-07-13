package com.sothawo.springdataelastictest

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException

@RestController
@RequestMapping("/repo")
class PersonRepositoryController(private val personRepository: PersonRepository) {

    @PostMapping("/person")
    fun savePerson(@RequestBody person: Person): String = personRepository.save(person).id!!.toString()

    @GetMapping("/persons")
    fun allPersons(): List<Person> = personRepository.findAllByOrderByFirstName()

    @GetMapping("/persons/{lastName}")
    fun byLastName(@PathVariable("lastName") lastName: String): List<Person> = personRepository.findByLastNameFuzzy(lastName)

    @GetMapping("/person/{id}")
    fun byId(@PathVariable("id") id: Long): Person = personRepository.findById(id).orElseThrow { HttpClientErrorException(HttpStatus.NOT_FOUND) }
}
