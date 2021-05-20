package com.sothawo.springdataelastictest.presidents;

import com.sothawo.springdataelastictest.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
class PresidentHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PresidentHandler.class);

    private final PresidentService service;
    PresidentHandler(PresidentService service){
        this.service = service;
    }

    public Mono<ServerResponse> load(ServerRequest request) {
        return ok().body(service.load(), President.class);
    }

    public Mono<ServerResponse> byId(ServerRequest request) {
        var id = request.pathVariable("id");
        var presidentMono = service.byId(id).switchIfEmpty(Mono.error(new ResourceNotFoundException()));
        return ok().body(presidentMono, President.class);
    }

    public Mono<ServerResponse> searchByTerm(ServerRequest request) {
        var year = Integer.parseInt(request.pathVariable("year"));
        return ok().body(service.searchByTerm(year), SearchHit.class);
    }

    public Mono<ServerResponse> searchByName(ServerRequest request) {
        var name = request.pathVariable("name");
        Boolean requestCache = null;
        var queryParam = request.queryParam("requestCache");
        if (queryParam.isPresent()) {
            requestCache = Boolean.parseBoolean(queryParam.get());
        }

        var searchHits = service.searchByName(name, requestCache);

        return ok().body(searchHits, SearchHit.class);
    }
}
