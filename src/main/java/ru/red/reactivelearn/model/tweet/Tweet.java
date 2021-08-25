package ru.red.reactivelearn.model.tweet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */

@Getter
@Setter
@ToString
@Document
@RequiredArgsConstructor
public class Tweet implements Serializable {
    @MongoId
    private UUID uuid;
    private UUID author;
    private Long creationTimestamp;
    private String contents;
}
