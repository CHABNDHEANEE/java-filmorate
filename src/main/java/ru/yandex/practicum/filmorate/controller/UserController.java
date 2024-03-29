package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Feed;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.RecommendationService;
import ru.yandex.practicum.filmorate.service.FeedService;
import ru.yandex.practicum.filmorate.service.UserDbService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@Component
@RequiredArgsConstructor
public class UserController {
    private final UserDbService userService;
    private final RecommendationService recommendationService;
    private final FeedService feedService;

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

    @GetMapping("/users/{id}/recommendations")
    public List<Film> recommendations(@PathVariable() int id) {
        return recommendationService.getRecommendation(id);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @GetMapping("/users/{id}/feed")
    public List<Feed> getFeed(@PathVariable("id") int id) {
        log.info("get feed by userId" + id);

        return feedService.getFeed(id);
    }
}