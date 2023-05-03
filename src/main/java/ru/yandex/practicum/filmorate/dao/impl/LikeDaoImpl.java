package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.DirectorDao;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.dao.LikeDao;
import ru.yandex.practicum.filmorate.dao.RatingDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class LikeDaoImpl implements LikeDao {
    private final JdbcTemplate jdbcTemplate;
    private final GenreDao genreDao;
    private final RatingDao ratingDao;
    private final DirectorDao directorDao;

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
    public List<Film> getMostPopularFilms(Integer filmsCount) {
        if (filmsCount == null) {
            filmsCount = 10;
        }
        String sql =
                "SELECT f.*, COUNT(l.user_id) " +
                        "FROM films AS f " +
                        "LEFT OUTER JOIN users_liked_films AS l " +
                        "ON l.film_id = f.film_id " +
                        "GROUP BY f.film_id " +
                        "ORDER BY COUNT(l.user_id) DESC " +
                        "LIMIT ?";

        return jdbcTemplate.query(sql, LikeDaoImpl.this::makeFilm, filmsCount);
    }

    @Override
    public List<Film> getMostPopularFilms(List<Integer> filmIds) {
        String filmsIdToString = filmIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",", "", ""));
        StringBuilder sqlb = new StringBuilder(
                "SELECT DISTINCT f.* " +
                        "FROM films AS f " +
                        "LEFT OUTER JOIN users_liked_films AS l " +
                        "ON l.film_id = f.film_id " +
                        "WHERE f.film_id IN()");
        sqlb.insert(sqlb.length() - 1, filmsIdToString);
        return jdbcTemplate.query(sqlb.toString(), LikeDaoImpl.this::makeFilm);
    }

    private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        return Film.builder()
                .id(rs.getInt("film_id"))
                .name(rs.getString("film_title"))
                .genres(genreDao.getGenresListForFilm(rs.getInt("film_id")))
                .description(rs.getString("film_description"))
                .releaseDate(rs.getDate("film_release_date").toLocalDate())
                .duration(rs.getInt("film_duration"))
                .mpa(ratingDao.getRatingById(rs.getInt("film_rating_id")))
                .directors(directorDao.getDirectorListForFilm(rs.getInt("film_id")))
                .build();
    }
}
