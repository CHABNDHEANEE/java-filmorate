package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface DirectorDao {
    Director addDirector(Director director);

    Director updateDirector(Director director);

    Director getDirectorById(int directorId);

    List<Director> getDirectorsList();

    List<Director> getDirectorListForFilm(int directorId);

    void deleteDirector(int directorId);

    void deleteDirectorForFilm(int filmId);

    List<Director> addDirectorToFilm(int filmId, List<Director> directors);

    /**
     * добавить методы, которые получают фильмы с определенным директором(1);
     * добавить методы, которые получают фильмы с директором по дате и кол-ву лайков(2)
     */
}
