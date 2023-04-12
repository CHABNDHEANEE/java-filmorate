package ru.yandex.practicum.filmorate.Dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikeDao {
    void like(int userId, int filmId);

    void unlike(int userId, int filmId);

    List<Film> getMostPopularFilms(int filmsCount);
}
