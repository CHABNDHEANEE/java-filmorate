package ru.yandex.practicum.filmorate.Dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Dao.FilmDao;
import ru.yandex.practicum.filmorate.Dao.LikeDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LikeDaoImpl implements LikeDao {
    private final JdbcTemplate jdbcTemplate;
    private final FilmDao filmDao;

    @Override
    public void like(int userId, int filmId) {
        String sql =
                "INSERT INTO users_liked_films (film_id, user_id) " +
                        "VALUES (?, ?)";

        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void unlike(int userId, int filmId) {
        String sql =
                "DELETE FROM users_liked_films WHERE user_id = ? AND film_id = ?";

        jdbcTemplate.update(sql, userId, filmId);
    }

    @Override
    public List<Film> getMostPopularFilms(int filmsCount) {
        String sql =
                "SELECT f.*" +
                        "FROM films AS f " +
                        "JOIN users_liked_film AS l " +
                        "ON l.film_id = f.film_id " +
                        "GROUP BY l.film_id " +
                        "ORDER BY COUNT(l.user_id) DESC " +
                        "LIMIT ?";

        return jdbcTemplate.query(sql, this::makeFilm, filmsCount);
    }

    private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        return Film.builder()
                .id(rs.getInt("film_id"))
                .title(rs.getString("film_title"))
                .genreId(rs.getInt("film_genre_id"))
                .description(rs.getString("film_description"))
                .releaseDate(rs.getDate("film_release_date").toLocalDate())
                .duration(rs.getInt("film_duration"))
                .ratingId(rs.getInt("film_rating_id"))
                .build();
    }
}
