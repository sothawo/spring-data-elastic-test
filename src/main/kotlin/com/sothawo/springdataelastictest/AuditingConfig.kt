package com.sothawo.springdataelastictest

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.userdetails.User

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
@EnableElasticsearchAuditing
class AuditingConfig {

    @Bean
    fun reactiveAuditorAware(): ReactiveAuditorAware<String>? = ReactiveAuditorAware {
        ReactiveSecurityContextHolder.getContext()
            .map { it.authentication }
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .map { (it as User).username }
    }
}
