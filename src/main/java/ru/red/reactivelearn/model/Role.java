package ru.red.reactivelearn.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */

@Setter
@Getter
@Document
public class Role implements Serializable {
    @MongoId
    private String authority;

    public Role(String authority) {
        this.authority = authority;
    }
}
