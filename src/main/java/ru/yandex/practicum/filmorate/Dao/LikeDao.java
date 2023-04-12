package ru.yandex.practicum.filmorate.Dao;

public interface LikeDao {
    void like(int userId, int filmId);

    void unlike(int userId, int filmId);

    void getMostPopularFilms(int filmsCount);
}
