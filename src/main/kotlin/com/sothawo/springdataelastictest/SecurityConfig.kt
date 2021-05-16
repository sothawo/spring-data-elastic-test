/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Bean
    public override fun userDetailsService(): UserDetailsService {
        val users = User.builder()
            .passwordEncoder { rawPassword: String? -> NoOpPasswordEncoder.getInstance().encode(rawPassword) }
        return InMemoryUserDetailsManager().apply {
            createUser(users.username("user").password("{noop}password").roles("USER").build())
            createUser(users.username("admin").password("{noop}password").roles("USER", "ADMIN").build())
        }
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.httpBasic()
            .and().authorizeRequests().anyRequest().authenticated()
            .and().csrf().disable()
            .formLogin().disable()
    }
}
