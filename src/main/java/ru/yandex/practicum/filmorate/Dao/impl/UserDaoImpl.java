package ru.yandex.practicum.filmorate.Dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    final JdbcTemplate jdbcTemplate;

    @Override
    public User addUser(User user) {
        String sql =
                "INSERT INTO users " +
                        "(user_email, user_login, user_name, user_birthday) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());

        return user;
    }

    @Override
    public void addFriend(int userId, int friendId) {
        String sql = "INSERT INTO friends (user_id, friend_id, friendship_status_id)" +
                "VALUES (?, ?, 1)";

        jdbcTemplate.update(sql,
                getUserById(userId),
                getUserById(friendId));
    }

    @Override
    public User getUserById(int userId) {
        String sql =
                "SELECT * FROM users WHERE user_id = ?";

        return jdbcTemplate.queryForObject(sql, this::makeUser, userId);
    }

    private User makeUser(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getInt("user_id"))
                .email(rs.getString("user_email"))
                .login(rs.getString("user_login"))
                .name(rs.getString("user_name"))
                .birthday(rs.getDate("user_birthday").toLocalDate())
                .build();
    }

    @Override
    public List<User> getFriends(int userId) {
        String sql =
                        "SELECT u.*" +
                        "FROM friends AS f " +
                        "JOIN users AS u " +
                        "ON f.friend_id = u.user_id" +
                        "WHERE f.user_id = ?";

        return jdbcTemplate.query(sql, this::makeUser, userId);
    }

    @Override
    public List<User> getCommonFriends(int userId, int friendId) {
        String sql =
                        "WITH u1 AS (" +
                                "SELECT u.*" +
                                "FROM friends AS f " +
                                "JOIN users AS u " +
                                "ON f.friend_id = u.user_id" +
                                "WHERE f.user_id = ?" +
                                "), " +
                        "u2 AS (" +
                                "SELECT u.*" +
                                "FROM friends AS f " +
                                "JOIN users AS u " +
                                "ON f.friend_id = u.user_id" +
                                "WHERE f.user_id = ?" +
                                ")" +
                        "SELECT u1.*" +
                                "FROM u1" +
                                "JOIN u2" +
                                "ON u1.user_id = u2.user_id";

        return jdbcTemplate.query(sql, this::makeUser, userId, friendId);
    }

    @Override
    public List<User> getUsersList(int max) {
        String sql =
                "SELECT * " +
                        "FROM users " +
                        "LIMIT ?";

        return jdbcTemplate.query(sql, this::makeUser, max);
    }
}
