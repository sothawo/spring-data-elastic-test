package com.sothawo.springdataelastictest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.elasticsearch.config.EnableReactiveElasticsearchAuditing;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;

@EnableReactiveElasticsearchAuditing
@SpringBootApplication(exclude = ElasticsearchDataAutoConfiguration.class)
@EnableReactiveElasticsearchRepositories(repositoryBaseClass = RoutingAwareReactiveElasticsearchRepositoryImpl.class)
public class SpringdataElasticTestApplication {

    @Autowired
    private ReactiveElasticsearchOperations operations;

    static {
        BlockHound.install(
            builder -> builder.allowBlockingCallsInside("org.elasticsearch.Build", "<clinit>")
                .allowBlockingCallsInside("org.elasticsearch.common.xcontent.XContentBuilder", "<clinit>")
                .allowBlockingCallsInside("com.github.javafaker.service.FakeValues", "loadValues")
                .blockingMethodCallback(it -> {
                    throw new BlockingOperationError(it);
                })
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringdataElasticTestApplication.class, args);
    }

    @Bean
    ReactiveAuditorAware<String> reactiveAuditorAware() {
        return () -> ReactiveSecurityContextHolder.getContext()
            .map(SecurityContext::getAuthentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .map(User.class::cast)
            .map(User::getUsername);
    }

    @EventListener(ApplicationReadyEvent.class)
    void logElasticVersions() {
        ((ReactiveElasticsearchTemplate) operations).logVersions().subscribe();
    }
}
