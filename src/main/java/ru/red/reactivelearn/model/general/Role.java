package ru.red.reactivelearn.model.general;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */

@Document
public enum Role implements GrantedAuthority, Serializable {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
