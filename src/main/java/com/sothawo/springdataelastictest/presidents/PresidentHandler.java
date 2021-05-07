package com.sothawo.springdataelastictest.presidents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
class PresidentHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PresidentHandler.class);

    private final PresidentRepository repository;

    PresidentHandler(PresidentRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> load(ServerRequest request) {
        Flux<President> presidentFlux = repository.deleteAll()
                .thenMany(repository.saveAll(Arrays.asList(
                        President.of("Dwight D Eisenhower", 1953, 1961),
                        President.of("Lyndon B Johnson", 1963, 1969),
                        President.of("Richard Nixon", 1969, 1974),
                        President.of("Gerald Ford", 1974, 1977),
                        President.of("Jimmy Carter", 1977, 1981),
                        President.of("Ronald Reagen", 1981, 1989),
                        President.of("George Bush", 1989, 1993),
                        President.of("Bill Clinton", 1993, 2001),
                        President.of("George W Bush", 2001, 2009),
                        President.of("Barack Obama", 2009, 2017),
                        President.of("Donald Trump", 2017, 2021),
                        President.of("Joe Biden", 2021, 2025))));
        return ok().body(presidentFlux, President.class);
    }
}
