package ru.red.reactivelearn.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.red.reactivelearn.model.tweet.Tweet;
import ru.red.reactivelearn.model.tweet.dto.TweetDto;

/**
 * @author Daniil Shreyder
 * Date: 25.08.2021
 */

@Mapper(componentModel = "spring")
public interface TweetMapper {

    @Mapping(source = "uuid", target = "uuid")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "creationTimestamp", target = "creationTimestamp")
    @Mapping(source = "contents", target = "contents")
    Tweet tweetDtoToTweet(TweetDto tweetDto);

    @Mapping(source = "uuid", target = "uuid")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "creationTimestamp", target = "creationTimestamp")
    @Mapping(source = "contents", target = "contents")
    TweetDto tweetToTweetDto(Tweet tweet);
}
