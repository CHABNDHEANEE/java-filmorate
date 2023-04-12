package ru.yandex.practicum.filmorate.Dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserDao {
    User addUser(User user);

    User getUserById(int userId);

    List<User> getFriends(int userId);

    List<User> getCommonFriends(int userId, int friendId);

    void addFriend(int userId, int friendId);

    List<User> getUsersList(int max);
}
