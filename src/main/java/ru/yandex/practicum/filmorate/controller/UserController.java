package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserListException;
import ru.yandex.practicum.filmorate.exception.WrongUserInputException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    private HashMap<Integer, User> users = new HashMap<>();

    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        user = checkUser(user);
        users.put(user.getId(), user);
        log.info("User added");
        return user;
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User user) {
        user = checkUser(user);
        users.put(user.getId(), user);
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
        if (user.getEmail().isBlank()) {
            log.info("Blank email err");
            throw new WrongUserInputException("Email can't be blank!");
        }
        if (!user.getEmail().contains("@")) {
            log.info("Incorrect email err");
            throw new WrongUserInputException("Incorrect user email!");
        }
        if (user.getLogin().contains(" ") || user.getLogin().isBlank()) {
            log.info("Incorrect user login err");
            throw new WrongUserInputException("Incorrect user login!");
        }
        if (user.getBirthDate().isAfter(LocalDate.now())) {
            log.info("Wrong bd err");
            throw new WrongUserInputException("You have a time machine! Don't ya?");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
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
    }
}
