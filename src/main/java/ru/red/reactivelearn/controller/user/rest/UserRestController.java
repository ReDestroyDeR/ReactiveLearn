package ru.red.reactivelearn.controller.user.rest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.red.reactivelearn.model.general.dto.UserDto;
import ru.red.reactivelearn.model.web.ServerResponse;
import ru.red.reactivelearn.service.UserService;

import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/user")
@PreAuthorize("hasAuthority({'USER'})")
public class UserRestController {
    private final UserService userService;

    @PostMapping
    public Mono<ServerResponse<UserDto>> save(@RequestBody UserDto userDto) {
        return userService.save(userDto)
                .map(ServerResponse::ok)
                .onErrorResume(ex -> Mono.just(ServerResponse.badRequest(ex)));
    }

    @GetMapping
    public Mono<ServerResponse<Page<UserDto>>> getPage(@RequestParam(required = false, defaultValue = "0") int page,
                                       @RequestParam(required = false, defaultValue = "10") int size,
                                       @RequestParam(required = false, defaultValue = "username") String sort) {
        return userService.findAllUsersPaged(PageRequest.of(page, size, Sort.by(sort.split("[+]"))))
                .map(ServerResponse::ok)
                .onErrorResume(ex -> Mono.just(ServerResponse.badRequest(ex)));
    }

    @GetMapping("/uuid")
    public Mono<ServerResponse<UserDto>> findById(@RequestParam("uuid") UUID uuid) {
        return userService.findById(uuid)
                .map(ServerResponse::ok)
                .switchIfEmpty(Mono.just(ServerResponse.notFound()));
    }

    @GetMapping("/username")
    public Mono<ServerResponse<UserDto>> findByUsername(@RequestParam("username") String username) {
        return userService.findByUsername(username)
                .map(ServerResponse::ok)
                .switchIfEmpty(Mono.just(ServerResponse.notFound()));
    }

    @DeleteMapping
    public Mono<ServerResponse<Void>> delete(@RequestBody UserDto userDto) {
        return userService.delete(userDto)
                .map(ServerResponse::ok)
                .onErrorResume(x -> Mono.just(ServerResponse.badRequest()));
    }
}
