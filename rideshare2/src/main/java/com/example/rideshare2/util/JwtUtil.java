package com.example.rideshare2.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    // IMPORTANT: HS256 requires a key of sufficient length (>= 256 bits). Replace with a secure random key in prod.
    private static final String SECRET = "replace_this_with_a_long_random_secret_at_least_32_chars!";
    private final SecretKey key;

    public JwtUtil() {
        // create a key from the SECRET string bytes
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username, String role) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 86_400_000L)) // 24 hours
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public String extractRole(String token) {
        // safe typed retrieval of "role" claim
        return getClaims(token).get("role", String.class);
    }

    public boolean validateToken(String token, String username) {
        String tokenUser = extractUsername(token);
        return tokenUser != null && tokenUser.equals(username) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        Date exp = getClaims(token).getExpiration();
        return exp == null || exp.before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
