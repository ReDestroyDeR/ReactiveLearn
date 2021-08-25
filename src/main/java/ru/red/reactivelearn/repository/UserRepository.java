package ru.red.reactivelearn.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.red.reactivelearn.model.general.dto.UserDto;

import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */

@Repository
public interface UserRepository extends ReactiveMongoRepository<UserDto, UUID> {
    Mono<UserDto> findByCreationTimestamp(long creationTimestamp);

    Mono<UserDto> findByUsername(String username);

    @Query(value = "{ 'username' : ?0 }", fields = " { 'following' : 1 }")
    Mono<UserDto> findFollowingByUsername(String username);

    @Query(value = "{ '_id' : ?0 }", fields = " { 'tweets' : 1 }")
    Mono<UserDto> findTweetsById(UUID uuid);
}
