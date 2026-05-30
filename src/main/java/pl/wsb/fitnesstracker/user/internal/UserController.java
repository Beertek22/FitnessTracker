package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.SimpleUserDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserController is responsible for handling HTTP requests related to user operations.
 * It provides endpoints for retrieving and creating users.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody UserDto userDto) throws InterruptedException {
        User user = userService.createUser(userMapper.mapDtoToUser(userDto));
        return userMapper.mapUserToDto(user);
    }

    @PutMapping("{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) throws InterruptedException {
        User createdUser = userService.updateUser(userId, userMapper.mapDtoToUser(userDto));
        return userMapper.mapUserToDto(createdUser);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers().stream().map(userMapper::mapUserToDto).collect(Collectors.toList());
    }

    @GetMapping("/simple")
    public List<SimpleUserDto> getAllSimpleUsers() {
        return userService.findAllUsers().stream().map(userMapper::mapUserToSimpleDto).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id).map(userMapper::mapUserToDto).orElse(null);
    }

    @GetMapping("/email")
    public List<UserDto> getUserByEmail(@RequestParam String email) {
        return userService.findAllUsers().stream()
                .filter(usr -> usr.getEmail().equals(email))
                .map(userMapper::mapUserToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/older/{time}")
    public List<UserDto> getAllUsersOlderThan(@PathVariable LocalDate time) {
        return userService.findAllUsers().stream()
                .filter(usr -> usr.getBirthdate().isBefore(time))
                .map(userMapper::mapUserToDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

}