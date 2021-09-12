package ru.red.reactivelearn.service.user;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.red.reactivelearn.model.general.Role;
import ru.red.reactivelearn.model.general.dto.UserDto;
import ru.red.reactivelearn.model.general.dto.security.UserAuthRequest;
import ru.red.reactivelearn.repository.UserRepository;
import ru.red.reactivelearn.service.pagination.PaginationService;

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
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PaginationService<UserDto, UUID> paginationService;

    @Override
    public Mono<UserDto> add(UserAuthRequest userAuthRequest) {
        UserDto user = new UserDto();
        user.setUuid(UUID.randomUUID());
        user.setCreationTimestamp(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
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
    public Mono<UserDto> save(UserDto user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<Page<UserDto>> findAllUsersPaged(Pageable pageable) {
        return paginationService.getPage(userRepository, pageable);
    }

    @Override
    public Mono<UserDto> findById(UUID uuid) {
        return userRepository.findById(uuid);
    }

    @Override
    public Mono<UserDto> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Flux<UserDto> findFollowingByUsername(String username) {
        return userRepository.findFollowingByUsername(username)
                .flatMapMany(user -> Flux.fromIterable(user.getFollowing())
                        .flatMap(userRepository::findById));
    }

    @Override
    public Mono<Void> delete(UserDto user) {
        return userRepository.delete(user);
    }
}
