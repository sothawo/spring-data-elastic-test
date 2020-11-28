/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.join;

import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Component
public class StatementHandler {

    private final StatementRepository repository;
    private final ReactiveElasticsearchTemplate operations;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public StatementHandler(StatementRepository repository, ReactiveElasticsearchTemplate operations) {
        this.repository = repository;
        this.operations = operations;
    }

    public Mono<ServerResponse> clear(ServerRequest request) {
        return ServerResponse.ok().body(repository.deleteAll(), Void.class);
    }

    Mono<ServerResponse> all(ServerRequest request) {
        return ServerResponse.ok().body(repository.searchAllBy(), SearchHit.class);
    }

    Mono<ServerResponse> save(ServerRequest request) {
        return ServerResponse
            .ok()
            .body(request
                    .bodyToMono(Statement.class)
                    .flatMap(repository::save),
                Statement.class
            );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        return ServerResponse
            .ok()
            .body(request.bodyToMono(Statement.class)
                    .flatMap(repository::delete),
                Void.class);
    }

    public Mono<ServerResponse> getByIdAndRouting(ServerRequest request) {

        var pathVariables = request.pathVariables();
        var id = request.pathVariable("id");
        var routing = pathVariables.getOrDefault("routing", null);

        return (routing != null ? operations.get(id, routing, Statement.class) : operations.get(id, Statement.class))
            .flatMap(it -> ServerResponse.ok().bodyValue(it))
            .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteByIdAndRouting(ServerRequest request) {
        var pathVariables = request.pathVariables();
        var id = request.pathVariable("id");
        var routing = pathVariables.getOrDefault("routing", null);

        return (routing != null ? operations.delete(id, routing, Statement.class) : operations.delete(id, Statement.class))
            .then(ServerResponse.ok().build())
            .switchIfEmpty(ServerResponse.notFound().build());
    }
}
