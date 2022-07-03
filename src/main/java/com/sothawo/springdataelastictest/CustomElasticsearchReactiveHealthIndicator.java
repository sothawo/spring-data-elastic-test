/*
 * Copyright 2012-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sothawo.springdataelastictest;

import co.elastic.clients.elasticsearch._types.HealthStatus;
import co.elastic.clients.elasticsearch.cluster.HealthResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.actuate.health.AbstractReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

/**
 * Custom {@link HealthIndicator} for an Elasticsearch cluster using a {@link ReactiveElasticsearchClient}.
 * <p>
 * Copied from Spring Boot code
 */
public class CustomElasticsearchReactiveHealthIndicator extends AbstractReactiveHealthIndicator {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final ReactiveElasticsearchClient reactiveElasticsearchClient;

    public CustomElasticsearchReactiveHealthIndicator(ReactiveElasticsearchClient reactiveElasticsearchClient) {
        super("Elasticsearch health check failed");
        this.reactiveElasticsearchClient = reactiveElasticsearchClient;
    }

    @Override
    protected Mono<Health> doHealthCheck(Health.Builder builder) {
        return reactiveElasticsearchClient.cluster().health(h -> h)
            .map(response -> builder
                .status(response.status() == HealthStatus.Red ? Status.OUT_OF_SERVICE : Status.UP)
                .withDetails(getDetails(response))
                .build())
            .onErrorResume(throwable -> Mono.just(builder.down(throwable).build()));
    }

    private Map<String, ?> getDetails(HealthResponse healthResponse) {
        Map<String, ?> details;
        try {
            details = OBJECT_MAPPER.readerFor(Map.class).readValue(healthResponse.toString());
        } catch (JsonProcessingException e) {
            details = Collections.singletonMap("Error parsing response body: ", e.getMessage());
        }
        return details;
    }
}
