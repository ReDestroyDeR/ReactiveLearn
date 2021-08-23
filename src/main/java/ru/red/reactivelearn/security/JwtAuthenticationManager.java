package ru.red.reactivelearn.security;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
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

@Component
@AllArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtils jwtUtils;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username = jwtUtils.getUsernameFromToken(authToken);
        return Mono.just(jwtUtils.validateJws(authToken))
                .filter(v -> v)
                .switchIfEmpty(Mono.empty())
                .map(valid -> {
                    Claims claims = jwtUtils.getAllClaimsForToken(authToken);
                    Collection<String> roles = claims.get("roles", Collection.class);
                    return new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                    );
                });
    }
}
