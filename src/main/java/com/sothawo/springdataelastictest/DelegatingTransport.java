/*
 * (c) Copyright 2023 sothawo
 */
package com.sothawo.springdataelastictest;

import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.Endpoint;
import co.elastic.clients.transport.TransportOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class DelegatingTransport implements ElasticsearchTransport {

    private static final Logger LOGGER = LoggerFactory.getLogger(DelegatingTransport.class);

    private final ElasticsearchTransport delegate;

    public DelegatingTransport(ElasticsearchTransport delegate) {
        this.delegate = delegate;
    }

    @Override
    public <RequestT, ResponseT, ErrorT> ResponseT performRequest(RequestT request, Endpoint<RequestT, ResponseT, ErrorT> endpoint, @Nullable TransportOptions options) throws IOException {
        LOGGER.info("delegating call performRequest");
        return delegate.performRequest(request, endpoint, options);
    }

    @Override
    public <RequestT, ResponseT, ErrorT> CompletableFuture<ResponseT> performRequestAsync(RequestT request, Endpoint<RequestT, ResponseT, ErrorT> endpoint, @Nullable TransportOptions options) {
        LOGGER.info("delegating call performRequestAsync");
        return delegate.performRequestAsync(request, endpoint, options);
    }

    @Override
    public JsonpMapper jsonpMapper() {
        LOGGER.info("delegating call jsonpMapper");
        return delegate.jsonpMapper();
    }

    @Override
    public TransportOptions options() {
        LOGGER.info("delegating call options");
        return delegate.options();
    }

    @Override
    public void close() throws IOException {
        LOGGER.info("delegating call close");
        delegate.close();
    }
}
