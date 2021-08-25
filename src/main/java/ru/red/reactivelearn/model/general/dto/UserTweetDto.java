package ru.red.reactivelearn.model.general.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 25.08.2021
 */

@Getter
@Setter
public class UserTweetDto {
    private UUID uuid;
    private String username;
}
