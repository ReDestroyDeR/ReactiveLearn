package ru.red.reactivelearn.model.tweet.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 25.08.2021
 */

@Getter
@Setter
public class TweetDto {
    private UUID uuid;
    private UUID author;
    private Long creationTimestamp;
    private String contents;
}
