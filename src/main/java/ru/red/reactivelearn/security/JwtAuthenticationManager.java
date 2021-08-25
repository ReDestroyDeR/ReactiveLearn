package ru.red.reactivelearn.security;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Daniil Shreyder
 * Date: 23.08.2021
 */

@Log
@Component
@AllArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtils jwtUtils;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        return Mono.fromCallable(() -> jwtUtils.validateJws(authToken))
                .filter(v -> v)
                .map(valid -> {
                    Claims claims = jwtUtils.getAllClaimsForToken(authToken);
                    Collection<String> roles = claims.get("roles", Collection.class);
                    return new UsernamePasswordAuthenticationToken(
                            jwtUtils.getUsernameFromToken(authToken),
                            null,
                            roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                    );
                });
    }
}
