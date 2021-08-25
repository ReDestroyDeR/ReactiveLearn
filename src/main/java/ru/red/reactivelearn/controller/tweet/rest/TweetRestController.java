package ru.red.reactivelearn.controller.tweet.rest;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.red.reactivelearn.mapper.TweetMapper;
import ru.red.reactivelearn.mapper.UserMapper;
import ru.red.reactivelearn.model.general.dto.UserDto;
import ru.red.reactivelearn.model.tweet.dto.TweetDto;
import ru.red.reactivelearn.model.tweet.dto.TweetPostDto;
import ru.red.reactivelearn.service.TweetService;
import ru.red.reactivelearn.service.UserService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 25.08.2021
 */

@Log
@RestController
@AllArgsConstructor
@RequestMapping("/api/user/tweet")
@PreAuthorize("hasAuthority('USER')")
public class TweetRestController {
    private final TweetService tweetService;
    private final UserService userService;
    private final TweetMapper tweetMapper;
    private final UserMapper userMapper;

    @PostMapping
    public Mono<ResponseEntity<TweetDto>> create(@AuthenticationPrincipal String username,
                                                 @RequestBody TweetPostDto tweetPostDto) {
        Mono<UserDto> userDto = userService.findByUsername(username);

        // Build tweetDto from tweetPostDto
        Mono<Pair<TweetDto, UserDto>> tweetDtoUserDtoPair = Mono.fromSupplier(() -> {
            TweetDto dto = new TweetDto();
            dto.setUuid(UUID.randomUUID());
            dto.setCreationTimestamp(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
            dto.setContents(tweetPostDto.getContents());
            return dto;
        })
                .zipWith(userDto, Pair::of)
                .map(pair -> {
                    pair.getFirst().setAuthor(pair.getSecond().getUuid());
                    return pair;
                });

        return tweetDtoUserDtoPair
                .flatMap(pair -> tweetService.add(
                        userMapper.userDtoToUser(pair.getSecond()),
                        tweetMapper.tweetDtoToTweet(pair.getFirst()))
                )
                .map(tweet -> ResponseEntity.ok(tweetMapper.tweetToTweetDto(tweet)));
        //.onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build()));
    }

    /* Code for checking if tweet already exists TODO: Patch mapping
    tweetDto.flatMap(tweet -> tweetService.findById(tweet.getUuid()))
                .flatMap(tweet -> user.flatMap(u -> !u.getUuid().equals(tweet.getUuid())
                                         ? Mono.error(new IllegalAccessException())
                                         : tweetService.save(tweetMapper.tweetDtoToTweet(tweetDto))))
     */

    @GetMapping
    public Mono<Page<TweetDto>> getPage(@RequestParam(required = false, defaultValue = "0") int page,
                                        @RequestParam(required = false, defaultValue = "10") int size) {
        return tweetService.findAllTweetsPaged(PageRequest.of(page, size))
                .map(pageInstance -> pageInstance.map(tweetMapper::tweetToTweetDto));
    }

    @GetMapping("/uuid")
    public Mono<ResponseEntity<TweetDto>> findById(@RequestParam("uuid") UUID uuid) {
        return tweetService.findById(uuid)
                .map(tweetMapper::tweetToTweetDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/contents")
    public Mono<ResponseEntity<TweetDto[]>> findByContents(@RequestParam("contents") String contents) {
        return tweetService.findByContent(contents)
                .map(tweetMapper::tweetToTweetDto)
                .collectList()
                .map(list -> list.toArray(TweetDto[]::new))
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> delete(@AuthenticationPrincipal String username,
                                          @RequestParam("uuid") UUID uuid) {
        return tweetService.findById(uuid)
                .flatMap(tweet ->
                        userService.findByUsername(username)
                                .flatMap(u -> !u.getUuid().equals(tweet.getAuthor())
                                        ? Mono.error(new IllegalAccessException())
                                        : tweetService.delete(uuid).map(ResponseEntity::ok)
                                )
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().build())));
    }
}
