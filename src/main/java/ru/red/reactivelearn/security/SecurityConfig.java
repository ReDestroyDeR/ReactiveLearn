package ru.red.reactivelearn.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;
import ru.red.reactivelearn.service.UserService;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain serverHttpSecurity(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity.formLogin().and().csrf().disable().build();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(UserService userService) {
        return username -> Mono.just(username).as(userService::findByUsername).cast(UserDetails.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
