package com.gateway.api_gateway.config;
import io.jsonwebtoken.Claims;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
@Component
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtProvider;

    public JwtAuthenticationFilter(JwtUtil jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // 1. Extract token from Authorization header
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // 2. Check if header has Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtProvider.validateToken(token)) {
                // 3. Extract username
                String username = jwtProvider.getUsernameFromToken(token);

                // 4. Create dummy auth (if roles available in JWT, extract them too)
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username, null, null);

                // 5. Set SecurityContext
                return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
            }
        }

        // 6. Continue without setting context
        return chain.filter(exchange);
    }
}
