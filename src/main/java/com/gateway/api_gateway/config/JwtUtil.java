package com.gateway.api_gateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private final String jwtSecret = "Z#4v8@RmP!1e^x*Lg2$sK!9wTb&EyQ3uMz^7dH@cNpA6fBj+UrXzV"; // Use same key as in Auth service

    // Extract username from JWT
    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    // Validate JWT token
    public boolean validateToken(String token) {
        try {
            getClaims(token); // Will throw if invalid
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}