package ru.red.reactivelearn.model.general;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Flux;
import ru.red.reactivelearn.model.general.dto.UserDto;
import ru.red.reactivelearn.model.tweet.Tweet;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */

@Getter
@Setter
@RequiredArgsConstructor
public class User implements UserDetails, Serializable {
    @MongoId
    private final UUID uuid;
    private final long creationTimestamp;
    private String username;
    private String password;
    private Collection<Role> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    private Flux<Tweet> tweets;
    private Flux<UserDto> followers;
    private Flux<UserDto> following;

    {
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.tweets = Flux.empty();
        this.followers = Flux.empty();
        this.following = Flux.empty();
    }

    // Excluding followers and following from equals and hashcode since there will be a chance for infinite recursion

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return uuid.equals(user.uuid)
                && username.equals(user.username)
                && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, username, password);
    }

    @Override
    public String toString() {
        return "User{" +
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
