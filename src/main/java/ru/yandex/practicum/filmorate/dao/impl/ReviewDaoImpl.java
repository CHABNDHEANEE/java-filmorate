package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.ReviewDao;
import ru.yandex.practicum.filmorate.exception.ObjectExistenceException;
import ru.yandex.practicum.filmorate.model.Review;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class ReviewDaoImpl implements ReviewDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Review addReview(Review review) {
        String sql =
                        "INSERT INTO reviews " +
                        "(content, is_positive, user_id, film_id) " +
                        "VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"review_id"});
            stmt.setString(1, review.getContent());
            stmt.setBoolean(2, review.isPositive());
            stmt.setInt(3, review.getUserId());
            stmt.setInt(4, review.getFilmId());
            return stmt;
        }, keyHolder);

        int id = (int) Objects.requireNonNull(keyHolder.getKey()).longValue();

        return getReviewById(id);
    }

    @Override
    public Review updateReview(Review review) {
        String sql =
                "UPDATE reviews SET " +
                        "content = ?, is_positive = ? " +
                        "WHERE review_id = ?";

        int changed = jdbcTemplate.update(sql,
                review.getContent(),
                review.isPositive(),
                review.getReviewId());

        if (changed == 0)
            throwObjectExistenceException();

        return getReviewById(review.getReviewId());
    }

    @Override
    public List<Review> getReviewList(int filmId, int count) {
        String sql = "SELECT * FROM reviews WHERE film_id = ? LIMIT ?";

        if (filmId == -1) {
            sql = "SELECT * FROM reviews LIMIT ?";
            return jdbcTemplate.query(sql, this::makeReview, count);
        }

        return jdbcTemplate.query(sql, this::makeReview, filmId, count);
    }

    @Override
    public void deleteReviewById(int id) {
        String sql =
                "DELETE FROM reviews WHERE review_id = ?";

        jdbcTemplate.update(sql, id);
    }

    @Override
    public Review getReviewById(int id) {
        String sql =
                "SELECT * " +
                        "FROM reviews " +
                        "WHERE review_id = ?";
        return jdbcTemplate.queryForObject(sql, this::makeReview, id);
    }

    private Review makeReview(ResultSet rs, int rowNum) throws SQLException {
        return Review.builder()
                .reviewId(rs.getInt("review_id"))
                .content(rs.getString("content"))
                .isPositive(rs.getBoolean("is_positive"))
                .userId(rs.getInt("user_id"))
                .filmId(rs.getInt("film_id"))
                .useful(rs.getInt("useful"))
                .build();
    }

        private void throwObjectExistenceException() {
            throw new ObjectExistenceException("Review Not Found!");
        }
}
