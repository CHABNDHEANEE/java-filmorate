package ru.yandex.practicum.filmorate.Dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmDao {
    public void addFilm(Film film);

    public Film getFilmById(int filmId);

    public List<Film> getFilmsList(int max);
}
