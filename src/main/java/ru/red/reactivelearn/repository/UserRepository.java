package ru.red.reactivelearn.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.red.reactivelearn.model.general.User;

import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, UUID> {
    Mono<User> findByCreationTimestamp(long creationTimestamp);
    Mono<User> findByUsername(String username);
}
