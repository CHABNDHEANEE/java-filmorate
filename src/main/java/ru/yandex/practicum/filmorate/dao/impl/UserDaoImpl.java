package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.auxilary.DaoHelper;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.ObjectExistenceException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final DaoHelper daoHelper;

    @Override
    public User addUser(User user) {
        String sql =
                "INSERT INTO users " +
                        "(user_email, user_login, user_name, user_birthday) " +
                "VALUES (?, ?, ?, ?)";

        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();

        java.sql.Date sqlDate = Date.valueOf(user.getBirthday());

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"user_id"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setDate(4, sqlDate);
            return stmt;
        }, keyHolder);

        return getUserById((int) Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public User updateUser(User user) {
        String sql =
                "UPDATE users SET " +
                        "user_email = ?, user_login = ?, user_name = ?, user_birthday = ? " +
                        "WHERE user_id = ?";

        int changed = jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());

        if (changed == 0) {
            throwObjectExistenceException();
        }

        return getUserById(user.getId());
    }

    @Override
    public void delete(int userId) {
        getUserById(userId);
        String sqlQuery = "DELETE FROM users WHERE user_id = ?";
        jdbcTemplate.update(sqlQuery, userId);
    }

    @Override
    public void addFriend(int userId, int friendId) {
        String sql = "INSERT INTO friends (user_id, friend_id, friendship_status_id) " +
                "VALUES (?, ?, 1)";

        jdbcTemplate.update(sql,
                getUserById(userId).getId(),
                getUserById(friendId).getId());

        checkConfirmation(userId, friendId);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        String sql = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";

        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public User getUserById(int userId) {
        String sql =
                "SELECT * FROM users WHERE user_id = ?";

        return jdbcTemplate.queryForObject(sql, daoHelper::makeUser, userId);
    }

    @Override
    public List<User> getFriends(int userId) {
        getUserById(userId);
        String sql =
                        "SELECT u.* " +
                        "FROM friends AS f " +
                        "JOIN users AS u " +
                        "ON f.friend_id = u.user_id " +
                        "WHERE f.user_id = ?";

        return jdbcTemplate.query(sql, daoHelper::makeUser, userId);
    }

    @Override
    public List<User> getCommonFriends(int userId, int friendId) {
        String sql =
                        "WITH u1 AS (" +
                                "SELECT u.* " +
                                "FROM friends AS f " +
                                "JOIN users AS u " +
                                "ON f.friend_id = u.user_id " +
                                "WHERE f.user_id = ? " +
                                "), " +
                        "u2 AS ( " +
                                "SELECT u.* " +
                                "FROM friends AS f " +
                                "JOIN users AS u " +
                                "ON f.friend_id = u.user_id " +
                                "WHERE f.user_id = ? " +
                                ") " +
                        "SELECT u1.* " +
                                "FROM u1 " +
                                "JOIN u2 " +
                                "ON u1.user_id = u2.user_id ";

        return jdbcTemplate.query(sql, daoHelper::makeUser, userId, friendId);
    }

    @Override
    public List<User> getUsersList(int max) {
        String sql =
                "SELECT * " +
                        "FROM users " +
                        "LIMIT ?";

        return jdbcTemplate.query(sql, daoHelper::makeUser, max);
    }

    private void checkConfirmation(int userId, int friendId) {
        String sql =
                "UPDATE friends AS f1 " +
                        "SET friendship_status_id = 2 " +
                        "WHERE user_id = ? AND user_id IN ( " +
                        "SELECT f2.friend_id FROM friends AS f2 " +
                        "WHERE f2.user_id = ? AND f2.user_id = f1.friend_id)";

        jdbcTemplate.update(sql, userId, friendId);

        sql =
                "UPDATE friends AS f1 " +
                        "SET friendship_status_id = 2 " +
                        "WHERE user_id = ? AND user_id NOT IN ( " +
                        "SELECT f2.friend_id FROM friends AS f2 " +
                        "WHERE f2.user_id = ? AND f2.user_id = f1.friend_id)";

        jdbcTemplate.update(sql, userId, friendId);
    }

    private void throwObjectExistenceException() {
        throw new ObjectExistenceException("User Not Found");
    }
}
