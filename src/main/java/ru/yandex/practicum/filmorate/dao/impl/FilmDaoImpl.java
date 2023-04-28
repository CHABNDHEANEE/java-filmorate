package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.DirectorDao;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.dao.RatingDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FilmDaoImpl implements FilmDao {
    final JdbcTemplate jdbcTemplate;
    private final RatingDao ratingDao;
    private final GenreDao genreDao;
    private final DirectorDao directorDao;

    @Override
    public Film addFilm(Film film) {
        String sql =
                        "INSERT INTO films " +
                        "(film_title, film_description, film_release_date, film_duration, film_rating_id) " +
                        "VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        java.sql.Date sqlDate = Date.valueOf(film.getReleaseDate());

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, sqlDate);
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);

        int id = (int) keyHolder.getKey().longValue();

        List<FilmGenre> filmGenres = genreDao.addGenresToFilm(id, film.getGenres());
        film.setGenres(filmGenres);
        List<Director> filmdirectors = directorDao.addDirectorToFilm(id, film.getDirectors());
        film.setDirectors(filmdirectors);

        return getFilmById(id);
    }

    @Override
    public Film getFilmById(int filmId) {
        String sql =
                "SELECT * FROM films WHERE film_id = ?";

        return jdbcTemplate.queryForObject(sql, this::makeFilm, filmId);
    }

    @Override
    public Film updateFilm(Film film) {
        String sql =
                "UPDATE films SET " +
                        "film_title = ?, film_description = ?, " +
                        "film_release_date = ?, film_duration = ?, film_rating_id = ? " +
                        "WHERE film_id = ?";

        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        genreDao.deleteGenresForFilm(film.getId());
        List<FilmGenre> filmGenres = genreDao.addGenresToFilm(film.getId(), film.getGenres());
        film.setGenres(filmGenres);
        directorDao.deleteDirectorForFilm(film.getId());
        List<Director> filmDirectors = directorDao.addDirectorToFilm(film.getId(), film.getDirectors());
        film.setDirectors(filmDirectors);

        return getFilmById(film.getId());
    }

    @Override
    public List<Film> getFilmsList(int max) {
        String sql =
                "SELECT * FROM films LIMIT ?";
        return jdbcTemplate.query(sql, this::makeFilm, max);
    }

    @Override
    public List<Film> getFilmWithDirectorSortByYear(int directorId) {
        String sql =
                "SELECT * FROM films WHERE film_id IN (SELECT film_id FROM film_director " +
                        "WHERE id = ?) ORDER BY film_release_date";

        return jdbcTemplate.query(sql, this::makeFilm, directorId);
    }

    @Override
    public List<Film> getFilmWithDirectorSortByLikes(int directorId) {
        String sql =
                "SELECT * FROM films WHERE film_id IN (SELECT film_id FROM film_director " +
                        "WHERE id = ?) ORDER BY film_rating_id DESC";

        return jdbcTemplate.query(sql, this::makeFilm, directorId);
    }

    private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        return Film.builder()
                .id(rs.getInt("film_id"))
                .name(rs.getString("film_title"))
                .genres(genreDao.getGenresListForFilm(rs.getInt("film_id")))
                .directors(directorDao.getDirectorListForFilm(rs.getInt("film_id")))
                .description(rs.getString("film_description"))
                .releaseDate(rs.getDate("film_release_date").toLocalDate())
                .duration(rs.getInt("film_duration"))
                .mpa(ratingDao.getRatingById(rs.getInt("film_rating_id")))
                .build();
    }
}
