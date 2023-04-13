package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<FilmGenre> getGenresList() {
        String sql =
                "SELECT * FROM genres";

        return jdbcTemplate.query(sql, this::makeGenre);
    }

    @Override
    public List<FilmGenre> getGenresListForFilm(int filmId) {
        String sql =
                "SELECT fg.*, g.genre_title FROM film_genre AS fg " +
                        "JOIN genres AS g ON g.genre_id = fg.genre_id " +
                        "WHERE fg.film_id = ?";

        return jdbcTemplate.query(sql, this::makeGenre, filmId);
    }

    @Override
    public void deleteGenresForFilm(int filmId) {
        String sql =
                "DELETE FROM film_genre " +
                        "WHERE film_id = ?";

        jdbcTemplate.update(sql, filmId);
    }

    @Override
    public List<FilmGenre> addGenresToFilm(int filmId, List<FilmGenre> genres) {
        String sql =
                "MERGE INTO film_genre " +
                        "(film_id, genre_id) " +
                        "KEY(film_id, genre_id) " +
                        "VALUES (?, ?)";

        if (genres == null || genres.isEmpty()) {
            return new ArrayList<>();
        }

        for (FilmGenre genre : genres) {
            jdbcTemplate.update(sql, filmId, genre.getId());
        }
        return getGenresListForFilm(filmId);
    }

    @Override
    public FilmGenre getGenreById(int genreId) {
        String sql =
                "SELECT * FROM genres WHERE genre_id = ?";

        return jdbcTemplate.queryForObject(sql, this::makeGenre, genreId);
    }

    private FilmGenre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new FilmGenre(rs.getInt("genre_id"),
                rs.getString("genre_title"));
    }
}
