package com.helmesbackend.task.helmes.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(Long personId) {
        log.info("Generating JWT token for person ID: {}", personId);
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("personId", personId);
            String token = createToken(claims);

            log.debug("Successfully generated JWT token for person ID: {}", personId);
            return token;
        } catch (Exception e) {
            log.error("Failed to generate JWT token for person ID: {}", personId, e);
            throw e;
        }
    }

    public Long extractPersonId(String token) {
        log.debug("Extracting person ID from JWT token");

        if (token == null || token.trim().isEmpty()) {
            log.warn("Cannot extract person ID: token is null or empty");
            return null;
        }

        try {
            Long personId = extractClaim(token, claims -> claims.get("personId", Long.class));
            log.debug("Successfully extracted person ID: {} from token", personId);
            return personId;
        } catch (io.jsonwebtoken.JwtException e) {
            log.warn("Failed to extract person ID from token due to JWT exception: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            log.error("Unexpected error while extracting person ID from token", e);
            return null;
        }
    }

    public boolean validateToken(String token) {
        log.debug("Validating JWT token");
        if (token == null || token.trim().isEmpty()) {
            log.warn("Token validation failed: token is null or empty");
            return false;
        }
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            log.error("Unexpected error during token validation", e);
            return false;
        }
    }

    private String createToken(Map<String, Object> claims) {
        SecretKey key = getSigningKey();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        SecretKey key = getSigningKey();
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}