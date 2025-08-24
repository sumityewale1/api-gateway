package com.gateway.api_gateway.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Defines the security filter chain for WebFlux (API Gateway).
     * - Disables CSRF (not needed for stateless REST APIs).
     * - Permits unauthenticated access to /api/auth/** and /users/signup.
     * - Requires authentication for all other endpoints.
     * - Runs in stateless mode (ideal for JWT).
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                // disable CSRF for APIs
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                // authorization rules
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/auth-service/api/auth/**", "/auth-service/users/signup").permitAll() // allow public endpoints
                        .pathMatchers("/actuator/**").permitAll()                  // allow actuator
                        .anyExchange().authenticated()                             // secure everything else
                )

                // disable form login and http basic (JWT will be used)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable);

        return http.build();
    }
}