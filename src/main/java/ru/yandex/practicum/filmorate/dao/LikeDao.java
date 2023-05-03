package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikeDao {
    void like(int userId, int filmId);

    void unlike(int userId, int filmId);

    List<Film> findPopularFilms(Integer limit, Integer genreId, String year);

    List<Film> findMostPopularFilms(List<Integer> filmIds);

}
