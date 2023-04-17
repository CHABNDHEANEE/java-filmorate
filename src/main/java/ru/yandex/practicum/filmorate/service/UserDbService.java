package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
@Qualifier
public class UserDbService {
    private final UserDao userDao;

    public UserDbService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User addUser(User user) {
        return userDao.addUser(user);
    }

    public User updateUser(User user) {
        return userDao.updateUser(user);
    }

    public void addFriend(int userId, int friendId) {
        userDao.addFriend(userId, friendId);
    }

    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    public List<User> getFriends(int userId) {
        return userDao.getFriends(userId);
    }

    public List<User> getCommonFriends(int userId, int friendId) {
        return userDao.getCommonFriends(userId, friendId);
    }

    public List<User> getUsersList(int max) {
        return userDao.getUsersList(max);
    }

    public void deleteFriend(int userId, int friendId) {
        userDao.deleteFriend(userId, friendId);
    }
}
