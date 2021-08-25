package ru.red.reactivelearn.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.red.reactivelearn.model.tweet.Tweet;

import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */

@Repository
public interface TweetRepository extends ReactiveMongoRepository<Tweet, UUID> {
    @Query(value = "{ 'contents' : { $regex : ?0, $options: 'i' } }")
    Flux<Tweet> findByContents(String query);
}
