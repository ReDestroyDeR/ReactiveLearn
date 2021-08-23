package ru.red.reactivelearn.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.red.reactivelearn.model.User;
import ru.red.reactivelearn.repository.UserRepository;

import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> save(Mono<User> user) {
        return user.flatMap(userRepository::save);
    }

    @Override
    public Mono<User> findById(Mono<UUID> uuid) {
        return uuid.flatMap(userRepository::findById);
    }

    @Override
    public Mono<User> findByUsername(Mono<String> username) {
        return username.flatMap(userRepository::findByUsername);
    }

    @Override
    public Mono<Void> delete(Mono<User> user) {
        return user.flatMap(userRepository::delete);
    }
}
