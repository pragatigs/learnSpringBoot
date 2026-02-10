package com.ecommerce.shopify.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${JWTsecretKey}")
    private String jwtSecretKey;

    public String extractUsernameFromToken(String jwtToken) {

        // if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        //     throw new RuntimeException("Missing or invalid Authorization header");
        // }

        // String token = authHeader.substring(7);

        SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        return claims.getSubject(); // username
    }

    public int getExpiration(String jwtToken) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        return (int) claims.getExpiration().getTime();
    }
}