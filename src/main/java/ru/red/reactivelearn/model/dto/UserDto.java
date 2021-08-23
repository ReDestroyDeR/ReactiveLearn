package ru.red.reactivelearn.model.dto;

import lombok.Getter;
import lombok.Setter;
import ru.red.reactivelearn.model.Role;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */

@Getter
@Setter
public class UserDto {
    private UUID uuid;
    private long creationTimestamp;
    private String username;
    private String password;
    private Role[] authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    private UUID[] tweets;
    private UUID[] followers;
    private UUID[] following;

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
                && password.equals(userDto.password)
                && Arrays.equals(authorities, userDto.authorities)
                && Arrays.equals(tweets, userDto.tweets)
                && Arrays.equals(followers, userDto.followers)
                && Arrays.equals(following, userDto.following);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(
                uuid, 
                creationTimestamp,
                username,
                password,
                accountNonExpired,
                accountNonLocked,
                credentialsNonExpired,
                enabled);
        result = 31 * result + Arrays.hashCode(authorities);
        result = 31 * result + Arrays.hashCode(tweets);
        result = 31 * result + Arrays.hashCode(followers);
        result = 31 * result + Arrays.hashCode(following);
        return result;
    }


}
