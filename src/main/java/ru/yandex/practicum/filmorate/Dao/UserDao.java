package ru.yandex.practicum.filmorate.Dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserDao {
    public User addUser(User user);

    public User getUserById(int userId);

    public List<User> getFriends(int userId);

    public List<User> getCommonFriends(int userId, int friendId);

    public void addFriend(int userId, int friendId);

    public List<User> getUsersList(int max);
}
