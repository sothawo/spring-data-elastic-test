package com.sothawo.springdataelastictest

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
@EnableElasticsearchAuditing
class AuditingConfig {

    @Bean
    fun auditorAware(): AuditorAware<String> = AuditorAware { Optional.ofNullable(SecurityContextHolder.getContext()?.authentication?.name) }
}
