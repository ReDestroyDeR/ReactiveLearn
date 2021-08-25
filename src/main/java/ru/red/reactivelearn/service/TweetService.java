package ru.red.reactivelearn.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.red.reactivelearn.model.general.User;
import ru.red.reactivelearn.model.tweet.Tweet;

import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 25.08.2021
 */
public interface TweetService {
    Mono<Tweet> add(User user,Tweet tweet);

    Mono<Tweet> save(Tweet tweet);

    Mono<Tweet> save(User user, Tweet tweet);

    Mono<Page<Tweet>> findAllTweetsFromFollowing(Flux<User> following, Pageable pageable);

    Mono<Page<Tweet>> findAllTweetsPaged(Pageable pageable);

    Mono<Tweet> findById(UUID uuid);

    Flux<Tweet> findByContent(String query);

    Mono<Void> delete(UUID uuid);
}
