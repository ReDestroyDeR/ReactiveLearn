package ru.red.reactivelearn.mapper;

import org.mapstruct.Mapper;
import ru.red.reactivelearn.model.tweet.Tweet;
import ru.red.reactivelearn.model.tweet.dto.TweetDto;

/**
 * @author Daniil Shreyder
 * Date: 25.08.2021
 */

@Mapper(componentModel = "spring")
public abstract class TweetMapper {

    public Tweet tweetDtoToTweet(TweetDto tweetDto) {
        if (tweetDto == null)
            return null;

        Tweet tweet = new Tweet();
        tweet.setUuid(tweetDto.getUuid());
        tweet.setAuthor(tweetDto.getAuthor());
        tweet.setCreationTimestamp(tweetDto.getCreationTimestamp());
        tweet.setContents(tweetDto.getContents());
        return tweet;
    }

    public TweetDto tweetToTweetDto(Tweet tweet) {
        if (tweet == null)
            return null;

        TweetDto tweetDto = new TweetDto();
        tweetDto.setUuid(tweet.getUuid());
        tweetDto.setAuthor(tweet.getAuthor());
        tweetDto.setCreationTimestamp(tweet.getCreationTimestamp());
        tweetDto.setContents(tweet.getContents());
        return tweetDto;
    }
}
