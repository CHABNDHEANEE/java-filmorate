package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmDao {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    Film getFilmById(int filmId);

    List<Film> getFilmsList(int max);

    List<Film> getFilmWithDirectorSortByYear(int directorId);

    public List<Film> getFilmWithDirectorSortByLikes(int directorId);
}
