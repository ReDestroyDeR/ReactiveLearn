package ru.red.reactivelearn.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.red.reactivelearn.mapper.UserMapper;
import ru.red.reactivelearn.model.general.dto.UserDto;
import ru.red.reactivelearn.service.UserService;

import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserRestController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Mono<ResponseEntity<UserDto>> save(@RequestBody UserDto userDto) {
        return userService.save(userMapper.userDtoToUser(userDto))
                .map(userMapper::userToUserDto)
                .map(ResponseEntity::ok)
                .onErrorResume(x -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @GetMapping("/uuid")
    @PreAuthorize("hasAnyAuthority({'USER', 'ADMIN'})")
    public Mono<ResponseEntity<UserDto>> findById(@RequestParam("uuid") UUID uuid) {
        return userService.findById(uuid)
                .map(userMapper::userToUserDto)
                .map(ResponseEntity::ok)
                .onErrorResume(x -> Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/username")
    @PreAuthorize("hasAnyAuthority({'USER', 'ADMIN'})")
    public Mono<ResponseEntity<UserDto>> findByUsername(@RequestParam("username") String username) {
        return userService.findByUsername(username)
                .map(userMapper::userToUserDto)
                .map(ResponseEntity::ok)
                .onErrorResume(x -> Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority({'USER', 'ADMIN'})")
    public Mono<ResponseEntity<Void>> delete(@RequestBody UserDto userDto) {
        return userService.delete((userMapper.userDtoToUser(userDto)))
                .map(ResponseEntity::ok)
                .onErrorResume(x -> Mono.just(ResponseEntity.badRequest().build()));
    }
}
