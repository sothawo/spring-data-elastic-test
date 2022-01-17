package com.sothawo.springdataelastictest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;
import org.springframework.data.elasticsearch.core.AbstractElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


@SpringBootApplication(exclude = ElasticsearchDataAutoConfiguration.class)
@EnableElasticsearchRepositories(namedQueriesLocation = "classpath:named-queries.properties")
@EnableElasticsearchAuditing
@EnableScheduling
public class SpringdataElasticTestApplication {

    @Autowired
    private ElasticsearchOperations operations;

    public static void main(String[] args) {
        SpringApplication.run(SpringdataElasticTestApplication.class, args);
    }

    @Bean
    AuditorAware<String> auditorAware() {
        return () -> {
            String userName = null;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                userName = authentication.getName();
            }
            return Optional.ofNullable(userName);
        };
    }

    @EventListener(ApplicationReadyEvent.class)
    void logElasticVersions() {
        ((AbstractElasticsearchTemplate) operations).logVersions();
    }

}
