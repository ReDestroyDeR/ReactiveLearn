package ru.red.reactivelearn.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
@Document
@RequiredArgsConstructor
public class Tweet implements Serializable {
    @MongoId
    private final UUID uuid;
    private final long creationTimestamp;
    private String contents;
}
