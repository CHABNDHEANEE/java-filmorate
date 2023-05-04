package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.DirectorDao;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.dao.RatingDao;
import ru.yandex.practicum.filmorate.exception.ObjectExistenceException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
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

        int id = (int) Objects.requireNonNull(keyHolder.getKey()).longValue();

        List<FilmGenre> filmGenres = genreDao.addGenresToFilm(id, film.getGenres());
        film.setGenres(filmGenres);
        List<Director> filmDirectors = directorDao.addDirectorToFilm(id, film.getDirectors());
        film.setDirectors(filmDirectors);

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
    public void deleteFilm(int filmId) {
        getFilmById(filmId);
        String sqlQuery = "DELETE FROM films " +
                "WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, filmId);
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

    @Override
    public List<Film> findCommonFilms(Integer userId, Integer friendId) {
        checkExistUserById(userId);
        checkExistUserById(friendId);

        return jdbcTemplate.query(
                "SELECT * " +
                        "FROM FILMS f " +
                        "LEFT JOIN FILM_RATING mpa ON f.FILM_RATING_ID = mpa.RATING_ID " +
                        "WHERE f.FILM_ID IN " +
                        "(SELECT ul.film_id " +
                        "FROM USERS_LIKED_FILMS ul " +
                        "INNER JOIN USERS_LIKED_FILMS fl ON ul.film_id = fl.film_id " +
                        "WHERE ul.user_id = ? AND fl.user_id = ?) " +
                        "ORDER BY FILM_RATING_ID DESC",
                this::makeFilm,
                userId, friendId
        );
    }

    @Override
    public List<Film> getFilmsSearchByTitle(String query) {
        String sql = "SELECT DISTINCT f.*, COUNT(l.user_id) AS count_likes " +
                "FROM films AS f " +
                "LEFT JOIN users_liked_films AS l ON f.film_id = l.film_id " +
                "WHERE f.film_title ILIKE '%'||?||'%' " +
                "GROUP BY f.film_id " +
                "ORDER BY count_likes DESC";
        return jdbcTemplate.query(sql, this::makeFilm, query);
    }

    @Override
    public List<Film> getFilmsSearchByDirectorAndTitle(String query) {
        String sql = "SELECT f.*, COUNT(l.user_id) AS count_likes " +
                "FROM films AS f " +
                "LEFT JOIN users_liked_films AS l ON f.film_id = l.film_id " +
                "WHERE f.film_title ILIKE '%'||?||'%' OR " +
                "f.film_id IN " +
                "(SELECT fd.film_id FROM film_director fd " +
                "LEFT JOIN director AS d ON fd.id = d.id " +
                "WHERE d.name ILIKE '%'||?||'%') " +
                "GROUP BY f.film_id " +
                "ORDER BY count_likes DESC";

        return jdbcTemplate.query(sql, this::makeFilm, query, query);
    }

    @Override
    public List<Film> getFilmsSearchByDirector(String query) {
        String sql = "SELECT f.*, d.name, COUNT(l.user_id) AS count_likes " +
                "FROM films AS f " +
                "JOIN film_director AS fd ON fd.film_id = f.film_id " +
                "JOIN director AS d ON d.id = fd.id " +
                "LEFT JOIN users_liked_films AS l ON f.film_id = l.film_id " +
                "WHERE d.name ILIKE '%'||?||'%' " +
                "GROUP BY f.film_id " +
                "ORDER BY count_likes DESC";

        return jdbcTemplate.query(sql, this::makeFilm, query);
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

    private void checkExistUserById(Integer userId) {
        Integer result = jdbcTemplate
                .queryForObject("SELECT count(USER_ID) FROM USERS WHERE USER_ID = ?", Integer.class, userId);

        if (result == null || result == 0) {
            throw new ObjectExistenceException("User with id='" + userId + "' not found");
        }
    }
}
