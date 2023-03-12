package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserListException;
import ru.yandex.practicum.filmorate.exception.WrongUserInputException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        return ;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        return ;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return ;
    }
}
