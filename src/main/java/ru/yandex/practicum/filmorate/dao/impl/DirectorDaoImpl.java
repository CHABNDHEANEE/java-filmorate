package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.DirectorDao;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Director;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DirectorDaoImpl implements DirectorDao {
    final JdbcTemplate jdbcTemplate;
    //private final FilmDao filmDao;
    private final ArrayList<Integer> listIdDirector = new ArrayList<>();

    @Override
    public Director addDirector(Director director) {
        String sql = "INSERT INTO director (name) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"id"});
            stmt.setString(1, director.getName());
            return stmt;
        }, keyHolder);

        int id = (int) keyHolder.getKey().longValue();
        listIdDirector.add(id);
        return getDirectorById(id);
    }

    @Override
    public Director getDirectorById(int directorId) {
        String sql =
                "SELECT * FROM director WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, this::makeDirector, directorId);
    }

    @Override
    public Director updateDirector(Director director) {
        String sql =
                "UPDATE director SET name = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                director.getName(),
                director.getId());
        return getDirectorById(director.getId());
    }

    @Override
    public List<Director> getDirectorsList() {
        String sql =
                "SELECT * FROM director ";

        return jdbcTemplate.query(sql, this::makeDirector);
    }

    @Override
    public List<Director> getDirectorListForFilm(int directorId) {
        String sql =
                "SELECT fd.*, d.name FROM film_director AS fd " +
                        "JOIN director AS d ON d.id = fd.id " +
                        "WHERE fd.film_id = ?";

        return jdbcTemplate.query(sql, this::makeDirector, directorId);
    }

    @Override
    public void deleteDirector(int directorId) {
        String request = "DELETE FROM director WHERE id=?";
        List<Director> directorsList = getDirectorsList();
        Director searchingDirector = null;
        for (Director director : directorsList) {
            if (director.getId() == directorId)
                searchingDirector = director;
        }
        jdbcTemplate.update(request,
                searchingDirector.getId());
    }

    @Override
    public void deleteDirectorForFilm(int filmId) {
        String sql =
                "DELETE FROM film_director " +
                        "WHERE film_id = ?";

        jdbcTemplate.update(sql, filmId);
    }


    public List<Director> getFilmWithDirectorSortByYear(int directorId) {
        String sql =
                "SELECT fd.*, d.name FROM film_director AS fd " +
                        "JOIN director AS d ON d.id = fd.id " +
                        "WHERE fd.film_id = ?";

        return jdbcTemplate.query(sql, this::makeDirector, directorId);
    }

    @Override
    public List<Director> addDirectorToFilm(int filmId, List<Director> directors) {
        String sql =
                "MERGE INTO film_director " +
                        "(film_id, id) " +
                        "KEY(film_id, id) " +
                        "VALUES (?, ?)";

        if (directors == null || directors.isEmpty()) {
            return new ArrayList<>();
        }

        for (Director director : directors) {
            jdbcTemplate.update(sql, filmId, director.getId());
        }
        return getDirectorListForFilm(filmId);
    }

    private Director makeDirector(ResultSet rs, int rowNum) throws SQLException {
        return new Director(rs.getInt("id"),
                rs.getString("name"));
    }
}
