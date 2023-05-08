package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmDao {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    Film getFilmById(int filmId);

    List<Film> getFilmsList(int max);

    void deleteFilm(int filmId);

    List<Film> getFilmWithDirectorSortByYear(int directorId);

    List<Film> getFilmWithDirectorSortByLikes(int directorId);

    List<Film> findCommonFilms(Integer userId, Integer friendId);

    List<Film> getFilmsSearchByTitle(String title);

    List<Film> getFilmsSearchByDirectorAndTitle(String query);

    List<Film> getFilmsSearchByDirector(String query);
}
