package ru.yandex.practicum.filmorate.Dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Dao.GenreDao;
import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<FilmGenre> getGenresList() {
        String sql =
                "SELECT * FROM film_genre";

        return jdbcTemplate.query(sql, this::makeGenre);
    }

    @Override
    public FilmGenre getGenreById(int genreId) {
        String sql =
                "SELECT * FROM film_genre WHERE genre_id = ?";

        return jdbcTemplate.queryForObject(sql, this::makeGenre, genreId);
    }

    private FilmGenre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new FilmGenre(rs.getInt("genre_id"),
                rs.getString("genre_title"));
    }
}
