package ru.red.reactivelearn.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.red.reactivelearn.model.general.Role;
import ru.red.reactivelearn.model.general.User;
import ru.red.reactivelearn.model.general.dto.security.UserAuthRequest;
import ru.red.reactivelearn.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> add(UserAuthRequest userAuthRequest) {
        User user = new User(
                UUID.randomUUID(),
                LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()
        );

        user.setUsername(userAuthRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userAuthRequest.getPassword()));
        user.setAuthorities(Set.of(Role.USER));

        return userRepository.findByUsername(user.getUsername())
                .hasElement()
                .flatMap(exists -> exists
                        ? Mono.error(new IllegalStateException("User already exists"))
                        : this.save(user));
    }

    @Override
    public Mono<User> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<User> findById(UUID uuid) {
        return userRepository.findById(uuid);
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Mono<Void> delete(User user) {
        return userRepository.delete(user);
    }
}
