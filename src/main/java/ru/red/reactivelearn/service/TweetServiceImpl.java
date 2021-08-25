package ru.red.reactivelearn.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.red.reactivelearn.model.general.User;
import ru.red.reactivelearn.model.tweet.Tweet;
import ru.red.reactivelearn.repository.TweetRepository;

import java.util.Comparator;
import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 25.08.2021
 */

@Service
@AllArgsConstructor
public class TweetServiceImpl implements TweetService {
    private final TweetRepository tweetRepository;
    private final UserService userService;
    private final PaginationService<Tweet, UUID> paginationService;

    @Override
    public Mono<Tweet> add(User user, Tweet tweet) {
        return findById(tweet.getUuid())
                .hasElement()
                .handle(((exists, tweetSynchronousSink) -> {
                    if (exists) {
                        tweetSynchronousSink.error(new IllegalStateException("Tweet already exists"));
                    } else {
                        this.save(tweet).subscribe(tweetSynchronousSink::next);
                    }
                }));
    }

    @Override
    public Mono<Tweet> save(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public Mono<Tweet> save(User user, Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public Mono<Page<Tweet>> findAllTweetsFromFollowing(Flux<User> following, Pageable pageable) {
        return paginationService.getPage(
                following.flatMap(User::getTweets)
                        .sort(Comparator.comparing(Tweet::getCreationTimestamp)),
                pageable);
    }

    @Override
    public Mono<Page<Tweet>> findAllTweetsPaged(Pageable pageable) {
        return paginationService.getPage(tweetRepository, pageable);
    }

    @Override
    public Mono<Tweet> findById(UUID uuid) {
        return tweetRepository.findById(uuid);
    }

    @Override
    public Flux<Tweet> findByContent(String query) {
        return tweetRepository.findByContents(query);
    }

    @Override
    public Mono<Void> delete(UUID uuid) {
        return tweetRepository.deleteById(uuid);
    }
}
