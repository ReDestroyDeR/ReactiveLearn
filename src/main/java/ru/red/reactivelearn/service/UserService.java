package ru.red.reactivelearn.service;

import reactor.core.publisher.Mono;
import ru.red.reactivelearn.model.general.User;
import ru.red.reactivelearn.model.general.dto.security.UserAuthRequest;

import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */
public interface UserService {

     Mono<User> add(UserAuthRequest user);

     Mono<User> save(User user);

     Mono<User> findById(UUID uuid);

     Mono<User> findByUsername(String username);

     Mono<Void> delete(User user);
}
