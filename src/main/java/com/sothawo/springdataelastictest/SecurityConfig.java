/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.*;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        User.UserBuilder users = User.builder().passwordEncoder(NoOpPasswordEncoder.getInstance()::encode);
        return new MapReactiveUserDetailsService(users.username("user").password("{noop}password").roles("USER").build(),
            users.username("admin").password("{noop}password").roles("USER", "ADMIN").build());
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .httpBasic(withDefaults())
            .authorizeExchange(exchanges ->
                exchanges
                    .anyExchange().authenticated()
            )
            .formLogin(it -> it.disable())
            .csrf(it -> it.disable());
        return http.build();
    }
}
