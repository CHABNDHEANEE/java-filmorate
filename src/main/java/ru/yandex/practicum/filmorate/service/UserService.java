package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User addUser(User user) {
        log.info("add user service");

        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        log.info("update user service");

        return userStorage.updateUser(user);
    }

    public User deleteUser(User user) {
        log.info("delete user service");

        return userStorage.deleteUser(user);
    }

    public void addFriend(User initUser, User requestedUser) {
        log.info("add friends");
        initUser.addFriend(requestedUser);
        requestedUser.addFriend(initUser);
    }

    public List<User> getFriendsList(User user) {
        log.info("get friends list");
        return new ArrayList<>(user.getFriendsList());
    }

    public List<User> getAllUsers() {
        log.info("get all users service");

        return userStorage.getAllUsers();
    }

    public void deleteFriend(User initUser, User requestedUser) {
        log.info("delete friend");
        initUser.addFriend(requestedUser);
        requestedUser.addFriend(initUser);
    }
}
