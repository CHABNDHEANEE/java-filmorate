package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.RatingDao;
import ru.yandex.practicum.filmorate.model.FilmRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RatingDaoImpl implements RatingDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<FilmRating> getRatingList() {
        String sql =
                "SELECT * FROM film_rating";

        return jdbcTemplate.query(sql, this::makeRating);
    }

    @Override
    public FilmRating getRatingById(int ratingId) {
        String sql =
                "SELECT * FROM film_rating WHERE rating_id = ?";

        return jdbcTemplate.queryForObject(sql, this::makeRating, ratingId);
    }

    private FilmRating makeRating(ResultSet rs, int rowNum) throws SQLException {
        return new FilmRating(rs.getInt("rating_id"),
                rs.getString("rating_name"));
    }
}
