package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@Component
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
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

        return userService.getUser(id);
    }

    @PostMapping("/users/{id}/friends/{friendId}")
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

        return userService.getFriendsList(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriendsList(@PathVariable int id, @PathVariable int otherId) {
        log.info("get common friends controller");

        return userService.getCommonFriends(id, otherId);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public void clearUserList() {
        userService.clearUserList();
    }
}
