package ru.yandex.practicum.filmorate.Dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FilmDaoImpl implements FilmDao {
    final JdbcTemplate jdbcTemplate;

    @Override
    public Film addFilm(Film film) {
        String sql =
                        "INSERT INTO films " +
                        "(film_title, film_genre_id, film_description, film_release_date, film_duration, film_rating_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        java.sql.Date sqlDate = Date.valueOf(film.getReleaseDate());

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"film_id"});
            stmt.setString(1, film.getTitle());
            stmt.setInt(2, film.getGenreId());
            stmt.setString(3, film.getDescription());
            stmt.setDate(4, sqlDate);
            stmt.setInt(5, film.getDuration());
            stmt.setInt(6, film.getRatingId());
            return stmt;
        }, keyHolder);

        return getFilmById((int) keyHolder.getKey().longValue());
    }

    @Override
    public Film getFilmById(int filmId) {
        String sql =
                "SELECT * FROM films WHERE film_id = ?";

        return jdbcTemplate.queryForObject(sql, this::makeFilm, filmId);
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

    @Override
    public Film updateFilm(Film film) {
        String sql =
                "UPDATE films SET " +
                        "film_title = ?, film_genre_id = ?, film_description = ?, " +
                        "film_release_date = ?, film_duration = ?, film_rating_id = ? " +
                        "WHERE film_id = ?";

        jdbcTemplate.update(sql,
                film.getTitle(),
                film.getGenreId(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRatingId(),
                film.getId());
        return film;
    }

    @Override
    public List<Film> getFilmsList(int max) {
        String sql =
                "SELECT * FROM films LIMIT ?";

        return jdbcTemplate.query(sql, this::makeFilm, max);
    }
}
