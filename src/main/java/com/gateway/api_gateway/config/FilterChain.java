package com.gateway.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class FilterChain {
    /**
     * Defines the security filter chain for handling all incoming HTTP requests.
     * - Disables CSRF protection for REST APIs
     * - Permits unauthenticated access to /api/auth/**
     * - Requires authentication for all other endpoints
     * - Sets the app to stateless mode
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // ❌ CSRF protection is mainly for browser-based apps with sessions.
                // ✅ We disable it here because we're building a stateless REST API (JWT-based).
                // 🔺 If you REMOVE this line: You may get 403 errors when sending POST/PUT requests without CSRF token.
                .csrf(csrf -> csrf.disable())

                // ✅ This line defines URL patterns that don’t require authentication.
                // 🔺 If you REMOVE this line: Even your `/api/auth/login` will require authentication, leading to 401 errors.
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/login", "/users/signup").permitAll()

                        // ✅ All other requests must be authenticated (i.e., must include a valid JWT).
                        // 🔺 If you REMOVE this line: All endpoints will be open to public, even protected ones.

                        .anyRequest().authenticated())

                // ✅ Tells Spring to not use sessions at all (ideal for JWT-based auth).
                // 🔺 If you REMOVE this line: Spring Security will create HTTP sessions, defeating statelessness of JWT.
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // NOTE: Once we implement JWT, we'll add a custom JwtAuthFilter here like:
        // .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
