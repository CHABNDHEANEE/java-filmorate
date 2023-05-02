package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.RecommendationsDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RecommendationDaoImpl implements RecommendationsDao {

    private final JdbcTemplate jdbcTemplate;

    public HashMap<Integer, List<Integer>> getNumTotalUsersFavoriteFilms() {
        String sql =
                "SELECT DISTINCT * " +
                        "FROM users_liked_films";

        return jdbcTemplate.query(sql, rs -> {
            HashMap<Integer, List<Integer>> results = new HashMap<>();
            while (rs.next()) {
                List<Integer> filmsIds = results.getOrDefault(rs.getInt("user_id"), new ArrayList<>());
                filmsIds.add(rs.getInt("film_id"));
                results.put(rs.getInt("user_id"), filmsIds);
            }
            return results;
        });
    }
}
