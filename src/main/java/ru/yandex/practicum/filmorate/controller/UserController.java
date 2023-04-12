package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ObjectExistenceException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserDbService;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@Component
@RequiredArgsConstructor
public class UserController {
    private final UserDbService userService;
    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        log.info("get user by id");

        return userService.getUserById(id);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        log.info("get user by id");

        return userService.getUsersList(10);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("add friend controller");

        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("delete friend controller");

        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getFriendsList(@PathVariable int id) {
        log.info("get friends list controller");

        return userService.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriendsList(@PathVariable int id, @PathVariable int otherId) {
        log.info("get common friends controller");

        return userService.getCommonFriends(id, otherId);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException e) {
        return Map.of("Validation error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final ValidationException e) {
        return Map.of("Validation error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleObjectExistenceException(final ObjectExistenceException e) {
        return Map.of("Object doesn't found", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleIncorrectResultSizeDataAccessException(final IncorrectResultSizeDataAccessException e) {
        return Map.of("Object doesn't found", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleRuntimeError(final Exception e) {
        return Map.of("Server error!", e.getMessage());
    }

//    public void clearUserList() {
//        userService.clearUserList();
//    }
}
