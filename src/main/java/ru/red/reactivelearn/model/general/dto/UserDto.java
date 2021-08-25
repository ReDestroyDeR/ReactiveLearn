package ru.red.reactivelearn.model.general.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import ru.red.reactivelearn.model.general.Role;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */

@Getter
@Setter
@Document
public class UserDto implements Serializable {
    @MongoId
    private UUID uuid;
    private long creationTimestamp;
    private String username;
    private String password;
    private Collection<Role> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    private Collection<UUID> tweets;
    private Collection<UUID> followers;
    private Collection<UUID> following;

    {
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.authorities = Collections.emptySet();
        this.tweets = Collections.emptySet();
        this.followers = Collections.emptySet();
        this.following = Collections.emptySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return creationTimestamp == userDto.creationTimestamp
                && accountNonExpired == userDto.accountNonExpired
                && accountNonLocked == userDto.accountNonLocked
                && credentialsNonExpired == userDto.credentialsNonExpired
                && enabled == userDto.enabled
                && uuid.equals(userDto.uuid)
                && username.equals(userDto.username)
                && password.equals(userDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                uuid,
                creationTimestamp,
                username,
                password,
                accountNonExpired,
                accountNonLocked,
                credentialsNonExpired,
                enabled);
    }


    @Override
    public String toString() {
        return "UserDto{" +
                "uuid=" + uuid +
                ", creationTimestamp=" + creationTimestamp +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                ", tweets=" + tweets +
                ", followers=" + followers +
                ", following=" + following +
                '}';
    }
}
