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

@SpringBootApplication(exclude = ElasticsearchDataAutoConfiguration.class)
@EnableReactiveElasticsearchRepositories
@EnableReactiveElasticsearchAuditing
public class SpringdataElasticTestApplication {
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
