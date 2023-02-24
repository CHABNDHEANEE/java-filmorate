package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserListException;
import ru.yandex.practicum.filmorate.exception.WrongUserInputException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    private Map<Integer, User> users = new HashMap<>();

    private int id = 1;

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        user = checkUser(user);

        setIdForUser(user);
        users.put(user.getId(), user);
        log.info("User added");
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        int userId = user.getId();

        user = checkUser(user);

        if (!users.containsKey(userId)) {
            log.info("wrong id update user");
            throw new WrongUserInputException("User with the id doesn't exist");
        }

        users.put(userId, user);
        log.info("User updated");
        return user;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        checkUsersListExistence();
        log.info("All users printed");
        return new ArrayList<>(users.values());
    }

    private User checkUser(User user) {
        if (user.getLogin().contains(" ")) {
            log.info("Incorrect user login err");
            throw new WrongUserInputException("Incorrect user login!");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.info("Wrong bd err");
            throw new WrongUserInputException("You have a time machine! Don't ya?");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getId() == -1) {
            user.setId(id);
            id++;
        }

        return user;
    }

    private void checkUsersListExistence() {
        if (users.isEmpty()) {
            log.info("User list empty error");
            throw new UserListException("User list is empty");
        }
    }

    public void clearUserList() {
        users = new HashMap<>();
        id = 1;
    }

    private void setIdForUser(User user) {
        user.setId(id);
        id++;
    }
}
