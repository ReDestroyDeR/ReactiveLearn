package ru.red.reactivelearn.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author Daniil Shreyder
 * Date: 23.08.2021
 */

@Log
@Component
public class JwtUtils {

    @Value("${security.jwt.secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Claims getAllClaimsForToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsForToken(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return getAllClaimsForToken(token).getExpiration().before(new Date());
    }

    public boolean validateJws(String token) {
        return !isTokenExpired(token);
    }

    public String createJws(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(
                        Date.from(LocalDateTime.now()
                                .plusHours(3)
                                .toInstant(ZoneOffset.UTC)
                        ))
                .signWith(key)
                .compact();
    }
}
