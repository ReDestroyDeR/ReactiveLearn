package ru.red.reactivelearn.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.red.reactivelearn.model.general.dto.UserDto;
import ru.red.reactivelearn.model.general.dto.security.UserAuthRequest;

import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */
public interface UserService {
    Mono<UserDto> add(UserAuthRequest user);

    Mono<UserDto> save(UserDto user);

    Mono<Page<UserDto>> findAllUsersPaged(Pageable pageable);

    Mono<UserDto> findById(UUID uuid);

    Mono<UserDto> findByUsername(String username);

    Flux<UserDto> findFollowingByUsername(String username);

    Mono<Void> delete(UserDto user);
}
