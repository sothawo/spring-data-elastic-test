/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        User.UserBuilder users = User.builder()
            .passwordEncoder(NoOpPasswordEncoder.getInstance()::encode);
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("{noop}password").roles("USER").build());
        manager.createUser(users.username("admin").password("{noop}password").roles("USER", "ADMIN").build());
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
            .and().
            authorizeRequests().anyRequest().authenticated()
            .and().csrf().disable()
            .formLogin().disable();
    }
}
