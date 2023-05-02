package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FeedDao;
import ru.yandex.practicum.filmorate.exception.ObjectExistenceException;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Feed;
import ru.yandex.practicum.filmorate.model.Operation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeedDaoImpl implements FeedDao {
    final JdbcTemplate jdbcTemplate;

    @Override
    public void addFeed(Integer entityId, Integer userId, long timeStamp, EventType eventType, Operation operation) {
        validateUser(userId);
        String sql = "INSERT INTO events (entity_id, user_Id, event_time, event_type, event_operation) " +
                "values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, userId, entityId, timeStamp, eventType.toString(), operation.toString());
    }

    @Override
    public List<Feed> getFeed(int id) {
        validateUser(id);
        String sql = "SELECT * FROM events WHERE user_id = ? ORDER BY event_id";
        return jdbcTemplate.query(sql, this::makeFeed, id);
    }

    Feed makeFeed(ResultSet rs, int rowNum) throws SQLException {
        return Feed.builder()
                .entityId(rs.getInt("entity_id"))
                .userId(rs.getInt("user_id"))
                .timestamp(rs.getLong("event_time"))
                .eventType(EventType.valueOf(rs.getString("event_type")))
                .operation(Operation.valueOf(rs.getString("event_operation")))
                .build();
    }
    private void validateUser(int userId) {
        String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, userId);

        if (!userRows.next()) {
            log.warn("User id {} not found", userId);
            throw new ObjectExistenceException("User id " + userId + " not found");
        }
    }

}
