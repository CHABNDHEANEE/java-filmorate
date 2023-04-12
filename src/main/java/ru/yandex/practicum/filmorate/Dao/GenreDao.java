package ru.yandex.practicum.filmorate.Dao;

import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.util.List;

public interface GenreDao {
    List<FilmGenre> getGenresList();

    List<FilmGenre> getGenresListForFilm(int filmId);

    List<FilmGenre> addGenresToFilm(int filmId, List<FilmGenre> genres);

    FilmGenre getGenreById(int genreId);

    void deleteGenresForFilm(int filmId);
}
