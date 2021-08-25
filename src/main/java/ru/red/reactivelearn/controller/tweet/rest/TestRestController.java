package ru.red.reactivelearn.controller.tweet.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.red.reactivelearn.mapper.TweetMapper;
import ru.red.reactivelearn.model.tweet.dto.TweetDto;
import ru.red.reactivelearn.model.web.ServerResponse;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 25.08.2021
 */

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class TestRestController {

    private final TweetMapper tweetMapper;

    @GetMapping("/test")
    public Mono<ServerResponse<?>> test1() {
        TweetDto dto = new TweetDto();
        dto.setUuid(UUID.randomUUID());
        dto.setCreationTimestamp(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
        dto.setContents("Test contents");
        return Mono.fromSupplier(() -> ServerResponse.ok(tweetMapper.tweetDtoToTweet(dto)));
    }
}
