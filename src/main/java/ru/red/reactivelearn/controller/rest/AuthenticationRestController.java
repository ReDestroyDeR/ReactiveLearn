package ru.red.reactivelearn.controller.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.red.reactivelearn.model.general.Role;
import ru.red.reactivelearn.model.general.User;
import ru.red.reactivelearn.model.general.dto.security.UserAuthRequest;
import ru.red.reactivelearn.model.general.dto.security.UserAuthResponse;
import ru.red.reactivelearn.security.JwtUtils;
import ru.red.reactivelearn.service.UserService;

/**
 * @author Daniil Shreyder
 * Date: 23.08.2021
 */

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationRestController {

    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public Mono<ResponseEntity<UserAuthResponse>> login(@RequestBody UserAuthRequest authRequest) {
        return userService.findByUsername(authRequest.getUsername())
                .filter(user -> passwordEncoder.matches(authRequest.getPassword(), user.getPassword()))
                .map(user -> ResponseEntity.ok(new UserAuthResponse(jwtUtils.createJws(user))))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PostMapping
    public Mono<ResponseEntity<UserAuthResponse>> register(@RequestBody UserAuthRequest authRequest) {
        return userService.add(authRequest)
                .map(user -> ResponseEntity.ok(new UserAuthResponse(jwtUtils.createJws(user))))
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().build()));
    }

    // lol endpoint. To be removed in the next commit
    @PutMapping
    public Mono<ResponseEntity<User>> upgradeToAdmin(Authentication authentication) {
        return userService.findByUsername(authentication.getName())
                .flatMap(user -> {
                    user.getAuthorities().add(Role.ADMIN);
                    return userService.save(user).map(ResponseEntity::ok);
                });
    }
}
