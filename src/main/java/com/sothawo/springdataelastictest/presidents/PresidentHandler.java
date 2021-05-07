package com.sothawo.springdataelastictest.presidents;

import com.sothawo.springdataelastictest.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
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
    private final ReactiveElasticsearchOperations operations;

    PresidentHandler(PresidentRepository repository, ReactiveElasticsearchOperations operations) {
        this.repository = repository;
        this.operations = operations;
    }

    public Mono<ServerResponse> load(ServerRequest request) {
        Flux<President> presidents = repository.deleteAll()
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
        return ok().body(presidents, President.class);
    }

    public Mono<ServerResponse> byId(ServerRequest request) {
        var id = request.pathVariable("id");
        var presidentMono = repository.findById(id).switchIfEmpty(Mono.error(new ResourceNotFoundException()));
        return ok().body(presidentMono, President.class);
    }

    public Mono<ServerResponse> searchByTerm(ServerRequest request) {
        var year = Integer.parseInt(request.pathVariable("year"));
        return ok().body(repository.searchByTerm(year), SearchHit.class);
    }

    public Mono<ServerResponse> searchByName(ServerRequest request) {
        var name = request.pathVariable("name");
        var requestCache = request.queryParam("requestCache")
            .map(Boolean::parseBoolean)
            .orElse(null);

        var query = new CriteriaQuery(new Criteria("name").is(name));
        if (requestCache != null) {
            query.setRequestCache(requestCache);
        }

        var searchHits = operations.count(query, President.class)
            .flatMapMany(
                count -> {
                    LOGGER.info("#presidents: {}", count);
                    return operations.search(query, President.class);
                }
            );

        return ok().body(searchHits, SearchHit.class);
    }
}
