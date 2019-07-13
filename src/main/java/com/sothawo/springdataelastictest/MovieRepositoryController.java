/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/movie")
public class MovieRepositoryController {

	private MovieRepository movieRepository;

	public MovieRepositoryController(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@GetMapping("/movies")
	public Iterable<Movie> getMovies() {
		return movieRepository.findAll();
	}

	@PostMapping("/movies")
	public String saveMovie(@RequestBody Movie movie) {
		return movieRepository.save(movie).getTitle();
	}

	@GetMapping("/title/{title}")
	public List<Movie> byTitle(@PathVariable("title") String title) {
		return movieRepository.findByTitle(title);
	}

	@GetMapping("/title3/{title}")
	public List<Movie> byTitle3(@PathVariable("title") String title) {
		return movieRepository.findFirst3ByTitle(title);
	}

	@GetMapping("/newest3/{title}")
	public List<Movie> newest3ByTitle(@PathVariable("title") String title) {
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
		movieRepository.saveAll(movies);
		return "#movies: " + movies.size();
	}
}
