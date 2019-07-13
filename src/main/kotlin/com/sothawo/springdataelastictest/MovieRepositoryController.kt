/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/movie")
class MovieRepositoryController(private val movieRepository: MovieRepository) {

    @GetMapping("/movies")
    fun allMovies(): Iterable<Movie> = movieRepository.findAll()

    @PostMapping("/movies")
    fun saveMovie(@RequestBody movie: Movie): String? = movieRepository.save(movie).title

    @GetMapping("/title/{title}")
    fun byTitle(@PathVariable("title") title: String): List<Movie> = movieRepository.findByTitle(title)

    @GetMapping("/title3/{title}")
    fun byTitle3(@PathVariable("title") title: String): List<Movie> = movieRepository.findFirst3ByTitle(title)

    @GetMapping("/newest3/{title}")
    fun newest3ByTitle(@PathVariable("title") title: String): List<Movie> = movieRepository.findFirst3ByTitleOrderByYearDesc(title)

    @PostMapping("load")
    fun loadMovies(): String {
        val objectMapper = ObjectMapper()

        val movies = objectMapper.readValue<List<Movie>>(File("movies.json"),
            objectMapper.typeFactory.constructCollectionType(List::class.java, Movie::class.java))

        var movieId: Long = 1

        movies.forEach { it.id = movieId++.toString() }

        movieRepository.saveAll(movies)
        return "#movies: " + movies.size
    }
}

