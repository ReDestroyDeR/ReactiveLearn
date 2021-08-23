package ru.red.reactivelearn.controller.rest;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.red.reactivelearn.mapper.UserMapper;
import ru.red.reactivelearn.model.User;
import ru.red.reactivelearn.model.dto.UserDto;
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

    @PostMapping()
    public Mono<UserDto> save(@RequestBody UserDto userDto) {
        return userService.save(Mono.just(userMapper.userDtoToUser(userDto))).map(userMapper::userToUserDto);
    }

    @GetMapping("/{uuid}")
    public Mono<User> findById(@PathVariable UUID uuid) {
        return userService.findById(Mono.just(uuid));
    }

    public Mono<User> findByUsername(String username) {
        return userService.findByUsername(Mono.just(username));
    }

    public Mono<Void> delete(UserDto userDto) {
        return userService.delete(Mono.just(userMapper.userDtoToUser(userDto)));
    }
}
