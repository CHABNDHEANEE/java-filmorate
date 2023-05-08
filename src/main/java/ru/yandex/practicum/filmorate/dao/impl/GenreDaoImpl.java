package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
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
