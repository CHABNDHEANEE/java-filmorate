package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FeedDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Instant;
import java.util.List;

import static ru.yandex.practicum.filmorate.model.EventType.FRIEND;
import static ru.yandex.practicum.filmorate.model.Operation.ADD;
import static ru.yandex.practicum.filmorate.model.Operation.REMOVE;

@Service
@Qualifier
public class UserDbService {
    private final UserDao userDao;
    private final FeedDao feedDao;

    public UserDbService(UserDao userDao, FeedDao feedDao) {
        this.userDao = userDao;
        this.feedDao = feedDao;
    }

    public User addUser(User user) {
        return userDao.addUser(user);
    }

    public User updateUser(User user) {
        checkExistenceOfUser(user.getId());
        return userDao.updateUser(user);
    }

    public void addFriend(int userId, int friendId) {
        checkExistenceOfUser(userId);
        checkExistenceOfUser(friendId);
        userDao.addFriend(userId, friendId);
        feedDao.addFeed(friendId, userId, Instant.now().toEpochMilli(), FRIEND, ADD);
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
        checkExistenceOfUser(userId);
        checkExistenceOfUser(friendId);
        userDao.deleteFriend(userId, friendId);
        feedDao.addFeed(friendId, userId, Instant.now().toEpochMilli(), FRIEND, REMOVE);
    }

    private void checkExistenceOfUser(int userId) {
        userDao.getUserById(userId);
    }

    public void deleteUser(int userId) {
        userDao.delete(userId);
    }
}
