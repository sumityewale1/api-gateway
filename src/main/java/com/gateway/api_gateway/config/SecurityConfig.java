package com.gateway.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final com.gateway.api_gateway.config.JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(com.gateway.api_gateway.config.JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Disable CSRF for simplicity
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/auth-service/api/auth/**").permitAll()
                        .anyExchange().authenticated()             // Secure others
                )
                .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION) // Register custom filter
                .build();
    }
}