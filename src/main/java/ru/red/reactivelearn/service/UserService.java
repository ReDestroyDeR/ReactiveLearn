package ru.red.reactivelearn.service;

import reactor.core.publisher.Mono;
import ru.red.reactivelearn.model.User;

import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */
public interface UserService {
     Mono<User> save(Mono<User> user);

     Mono<User> findById(Mono<UUID> uuid);

     Mono<User> findByUsername(Mono<String> username);

     Mono<Void> delete(Mono<User> user);
}
