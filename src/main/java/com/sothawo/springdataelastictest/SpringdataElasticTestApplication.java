package com.sothawo.springdataelastictest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.elasticsearch.config.EnableReactiveElasticsearchAuditing;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import reactor.blockhound.BlockHound;

@SpringBootApplication(exclude = ElasticsearchDataAutoConfiguration.class)
@EnableReactiveElasticsearchRepositories
@EnableReactiveElasticsearchAuditing
public class SpringdataElasticTestApplication {

    static {
        BlockHound.install(
            builder -> builder.allowBlockingCallsInside("org.springframework.data.elasticsearch.support.VersionInfo", "logVersions"),
            builder -> builder.allowBlockingCallsInside("org.springframework.data.elasticsearch.core.index.MappingBuilder", "addRuntimeFields"),
            builder -> builder.allowBlockingCallsInside("org.elasticsearch.Build", "<clinit>"),
            builder -> builder.allowBlockingCallsInside("org.elasticsearch.common.xcontent.XContentBuilder", "<clinit>"),
            builder -> builder.allowBlockingCallsInside("com.github.javafaker.service.FakeValues", "loadValues"),
            builder -> builder.blockingMethodCallback(it -> {
                new Error(it.toString()).printStackTrace();
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
}
