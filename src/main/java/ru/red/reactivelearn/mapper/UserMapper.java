package ru.red.reactivelearn.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import ru.red.reactivelearn.model.general.User;
import ru.red.reactivelearn.model.general.dto.UserDto;
import ru.red.reactivelearn.model.tweet.Tweet;
import ru.red.reactivelearn.repository.TweetRepository;
import ru.red.reactivelearn.repository.UserRepository;

import java.util.stream.Collectors;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected TweetRepository tweetRepository;

    /**
     * User -> UserDto mapper
     *
     * @param user input
     * @return <code>UserDto</code> representation
     */
    public UserDto userToUserDto(User user) {
        return singleUserToUserDto(user);
    }

    /**
     * UserDto -> User mapper
     *
     * @param userDto input
     * @return <code>User</code> representation
     */
    public User userDtoToUser(UserDto userDto) {
        return singleUserDtoToUser(userDto);
    }

    // Two methods below are working with blocking types so I do not expose them
    private UserDto singleUserToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setUuid(user.getUuid());
        dto.setCreationTimestamp(user.getCreationTimestamp());
        dto.setAuthorities(user.getAuthorities());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());

        dto.setFollowers(user.getFollowers().map(UserDto::getUuid).toStream().collect(Collectors.toSet()));
        dto.setFollowing(user.getFollowing().map(UserDto::getUuid).toStream().collect(Collectors.toSet()));
        dto.setTweets(user.getTweets().map(Tweet::getUuid).toStream().collect(Collectors.toSet()));

        dto.setAccountNonExpired(user.isAccountNonExpired());
        dto.setAccountNonLocked(user.isAccountNonLocked());
        dto.setCredentialsNonExpired(user.isCredentialsNonExpired());
        dto.setEnabled(user.isEnabled());
        return dto;
    }

    private User singleUserDtoToUser(UserDto userDto) {
        User user = new User(userDto.getUuid(), userDto.getCreationTimestamp());
        user.setAuthorities(userDto.getAuthorities());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        user.setFollowers(Flux.fromIterable(userDto.getFollowers())
                .flatMap(userRepository::findById));

        user.setFollowing(Flux.fromIterable(userDto.getFollowing())
                .flatMap(userRepository::findById));

        user.setTweets(Flux.fromIterable(userDto.getTweets())
                .flatMap(tweetRepository::findById));

        user.setAccountNonExpired(userDto.isAccountNonExpired());
        user.setAccountNonLocked(userDto.isAccountNonLocked());
        user.setCredentialsNonExpired(userDto.isCredentialsNonExpired());
        user.setEnabled(userDto.isEnabled());
        return user;
    }
}
