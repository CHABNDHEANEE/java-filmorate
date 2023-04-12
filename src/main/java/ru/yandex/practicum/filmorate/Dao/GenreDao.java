package ru.yandex.practicum.filmorate.Dao;

import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.util.List;

public interface GenreDao {
    List<FilmGenre> getGenresList();

    FilmGenre getGenreById(int genreId);
}
