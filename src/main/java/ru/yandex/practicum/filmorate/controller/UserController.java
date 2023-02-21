package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.WrongUserInputException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    private HashMap<Integer, User> users;

    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        checkUser(user);
        users.put(user.getId(), user);
        log.info("User added");
        return user;
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User user) {
        checkUser(user);
        users.put(user.getId(), user);
        log.info("User updated");
        return user;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        log.info("All users printed");
        return new ArrayList<>(users.values());
    }

    private void checkUser(User user) {
        if (user.getEmail().isBlank()) {
            log.info("Blank email err");
            throw new WrongUserInputException("Email can't be blank!");
        }
        if (!user.getEmail().contains("@")) {
            log.info("Incor email err");
            throw new WrongUserInputException("Incorrect user email!");
        }
        if (user.getLogin().contains(" ") || user.getLogin().isBlank()) {
            log.info("Incor user login err");
            throw new WrongUserInputException("Incorrect user login!");
        }
        if (user.getBirthDate().isAfter(LocalDate.now())) {
            log.info("Wrong bd err");
            throw new WrongUserInputException("You have a time machine! Don't ya?");
        }
    }
}
