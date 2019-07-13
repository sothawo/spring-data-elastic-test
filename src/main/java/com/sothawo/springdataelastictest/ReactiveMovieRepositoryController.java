/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/movie")
public class ReactiveMovieRepositoryController {

    private ReactiveMovieRepository movieRepository;

    public ReactiveMovieRepositoryController(ReactiveMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("movies")
    public Flux<Movie> allMovies() {
        return movieRepository.findAll();
    }

    @PostMapping("/movies")
    public String saveMovie(@RequestBody Movie movie) {
        return movieRepository.save(movie).block().getTitle();
    }

    @GetMapping("/title/{title}")
    public Flux<Movie> byTitle(@PathVariable("title") String title) {
        return movieRepository.findByTitle(title);
    }

    @GetMapping("/titleWithCount/{title}")
    private Flux<Movie> getMovieFluxWithPageables(@PathVariable("title") String title) {
        List<Movie> movieList = new ArrayList<>();
        Long count = movieRepository.countByTitle(title).block();
        int page = 0;
        while (movieList.size() < count) {
            Flux<Movie> movies = movieRepository.findByTitle(title, PageRequest.of(page++, 5));
            movieList.addAll(movies.collectList().block());
        }
        return Flux.fromStream(movieList.stream());
    }

    @GetMapping("/title3/{title}")
    public Flux<Movie> byTitle3(@PathVariable("title") String title) {
        return movieRepository.findFirst3ByTitle(title);
    }

    @GetMapping("/newest3/{title}")
    public Flux<Movie> newest3ByTitle(@PathVariable("title") String title) {
        return movieRepository.findFirst3ByTitleOrderByYearDesc(title);
    }

    @PostMapping("load")
    public String loadMovies() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Movie> movies = objectMapper.readValue(new File("movies.json"),
            objectMapper.getTypeFactory().constructCollectionType(List.class, Movie.class));

        long id = 1;

        for (Movie movie : movies) {
            movie.setId(String.valueOf(id++));
        }
        movieRepository.saveAll(movies).blockLast();
        return "#movies: " + movies.size();
    }
}
