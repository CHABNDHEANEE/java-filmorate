package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FeedDao;
import ru.yandex.practicum.filmorate.exception.ObjectExistenceException;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Feed;
import ru.yandex.practicum.filmorate.model.Operation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeedDaoImpl implements FeedDao {
    final JdbcTemplate jdbcTemplate;

    @Override
    public void addFeed(int entityId, int userId, long timeStamp, EventType eventType, Operation operation) {
        validateUser(userId);

        String sql = "INSERT INTO events (entity_id, user_Id, event_time, event_type, event_operation) " +
                "values (?, ?, ?, ?, ?)";

        Feed event = Feed.builder()
                .eventType(eventType)
                .userId(userId)
                .timestamp(timeStamp)
                .entityId(entityId)
                .operation(operation)
                .build();

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"event_id"});
            stmt.setInt(1, entityId);
            stmt.setInt(2, userId);
            stmt.setLong(3, timeStamp);
            stmt.setString(4, eventType.toString());
            stmt.setString(5, operation.toString());
            return stmt;
        }, keyHolder);

        event.setEventId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        //jdbcTemplate.update(sql, entityId, userId, timeStamp, eventType.toString(), operation.toString());
    }

    @Override
    public List<Feed> getFeed(int id) {
        validateUser(id);
        String sql = "SELECT * FROM events WHERE user_id = ? ORDER BY event_id";
        return jdbcTemplate.query(sql, this::makeFeed, id);
    }

    Feed makeFeed(ResultSet rs, int rowNum) throws SQLException {
        return Feed.builder()
                .eventId(rs.getInt("event_id"))
                .entityId(rs.getInt("entity_id"))
                .userId(rs.getInt("user_id"))
                .timestamp(rs.getLong("event_time"))
                .eventType(EventType.valueOf(rs.getString("event_type")))
                .operation(Operation.valueOf(rs.getString("event_operation")))
                .build();
    }

    private void validateUser(int userId) {
        String userCheck =
                "SELECT * FROM users WHERE user_id = ?";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(userCheck, userId);
        if (!userRows.next()) {
            throw new ObjectExistenceException("User " + userId + " Not Found");
        }
    }
}
