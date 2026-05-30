package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.SimpleUserDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;

@Component
class UserMapper {

    public UserDto mapUserToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    public User mapDtoToUser(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }

    public SimpleUserDto mapUserToSimpleDto(User user) {
        return new SimpleUserDto(user.getFirstName(), user.getLastName());
    }
}
