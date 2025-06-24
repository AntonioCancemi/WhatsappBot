package com.whatsbot.auth.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final Key key;

    public JwtService(@Value("${whatsapp.app-secret:secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UUID userId, String username, UUID tenantId) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .claim("uid", userId.toString())
                .claim("sub", username)
                .claim("tid", tenantId.toString())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 3600_000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public UUID extractUser(String token) {
        try {
            String id = Jwts.parser().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody().get("uid", String.class);
            return id != null ? UUID.fromString(id) : null;
        } catch (Exception e) {
            return null;
        }
    }

    public UUID extractTenant(String token) {
        try {
            String id = Jwts.parser().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody().get("tid", String.class);
            return id != null ? UUID.fromString(id) : null;
        } catch (Exception e) {
            return null;
        }
    }

    public String extractUsername(String token) {
        try {
            return Jwts.parser().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody().get("sub", String.class);
        } catch (Exception e) {
            return null;
        }
    }
}
