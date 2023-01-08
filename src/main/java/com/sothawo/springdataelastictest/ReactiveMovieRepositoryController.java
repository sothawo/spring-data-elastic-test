/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/movie")
public class ReactiveMovieRepositoryController {

	private RecativeMovieRepository movieRepository;

	public ReactiveMovieRepositoryController(RecativeMovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@GetMapping("movies")
	public Flux<Movie> allMovies() {
		return movieRepository.findAll();
	}

	@PostMapping("/movies")
	public Mono<String> saveMovie(@RequestBody Movie movie) {
		return movieRepository.save(movie).map(Movie::getTitle);
	}

	@GetMapping("/title/{title}")
	public Flux<SearchHit<Movie>> byTitle(@PathVariable("title") String title) {
		return movieRepository.findByTitle(title);
	}

	private Flux<SearchHit<Movie>> getMovieFluxWithPageables(@PathVariable("title") String title) {
		List<SearchHit<Movie>> movieList = new ArrayList<>();
		Long count = movieRepository.countByTitle(title).block();
		int page = 0;
		while (movieList.size() < count) {
			Flux<SearchHit<Movie>> movies = movieRepository.findByTitle(title, PageRequest.of(page++, 5));
			movieList.addAll(movies.collectList().block());
		}
		return Flux.fromStream(movieList.stream());
	}

	@GetMapping("/title3/{title}")
	public Flux<SearchHit<Movie>> byTitle3(@PathVariable("title") String title) {
		return movieRepository.findFirst3ByTitle(title);
	}

	@GetMapping("/newest3/{title}")
	public Flux<SearchHit<Movie>> newest3ByTitle(@PathVariable("title") String title) {
		return movieRepository.findFirst3ByTitleOrderByYearDesc(title);
	}

	@PostMapping("/load")
	public Mono<String> loadMovies() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		List<Movie> allMovies = objectMapper.readValue(new File("movies.json"),
				objectMapper.getTypeFactory().constructCollectionType(List.class, Movie.class));

		long id = 1;

		for (Movie movie : allMovies) {
			movie.setId(String.valueOf(id++));
		}
		var groupedMovies =
				allMovies.stream().collect(Collectors.groupingBy(movie -> Long.valueOf(movie.getId()) / 1000));

		return Flux.fromIterable(groupedMovies.values())
				.map(movies -> {
					return movieRepository.saveAll(movies);
				})
				.then(Mono.just("#movies: " + allMovies.size()));
	}
}
