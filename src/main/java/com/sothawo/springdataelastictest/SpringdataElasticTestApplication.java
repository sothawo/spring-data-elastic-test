package com.sothawo.springdataelastictest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.config.EnableReactiveElasticsearchAuditing;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;

@EnableReactiveElasticsearchAuditing(auditorAwareRef = "myReactiveAuditorAware")
@SpringBootApplication
@EnableReactiveElasticsearchRepositories(repositoryBaseClass = RoutingAwareReactiveElasticsearchRepositoryImpl.class)
@ImportRuntimeHints(TestAppRuntimeHints.class)
public class SpringdataElasticTestApplication {

	@Autowired
	private ReactiveElasticsearchOperations operations;

	// comment out when running as native application
/*
	static {
		BlockHound.install(
				builder -> builder
						.allowBlockingCallsInside("co.elastic.clients.transport.rest_client.RestClientTransport", "performRequestAsync")
						.allowBlockingCallsInside("com.fasterxml.jackson.databind.ObjectMapper", "readValue")
						.blockingMethodCallback(it -> {
							throw new BlockingOperationError(it);
						})
		);
	}
*/

	public static void main(String[] args) {
		SpringApplication.run(SpringdataElasticTestApplication.class, args);
	}

	@Bean
	ReactiveAuditorAware<String> myReactiveAuditorAware() {
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
