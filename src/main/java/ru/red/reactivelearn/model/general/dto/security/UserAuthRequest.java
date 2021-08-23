package ru.red.reactivelearn.model.general.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Daniil Shreyder
 * Date: 23.08.2021
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthRequest {
    private String username;
    private String password;
}
