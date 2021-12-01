/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

	@Bean
	fun userDetailsService(): MapReactiveUserDetailsService? {
		val users = User.builder().passwordEncoder { rawPassword: String? ->
			NoOpPasswordEncoder.getInstance().encode(rawPassword)
		}
		return MapReactiveUserDetailsService(
			users.username("user").password("{noop}password").roles("USER").build(),
			users.username("admin").password("{noop}password").roles("USER", "ADMIN").build()
		)
	}

	@Bean
	fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
		http
			.httpBasic(Customizer.withDefaults())
			.authorizeExchange { exchanges: AuthorizeExchangeSpec ->
				exchanges.anyExchange().authenticated()
			}
			.formLogin { it.disable() }
			.csrf { it.disable() }
		return http.build()
	}

}
