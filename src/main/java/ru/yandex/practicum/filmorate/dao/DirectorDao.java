package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

public interface DirectorDao {
    Director addDirector(Director director);

    Director updateDirector(Director director);

    Director getDirectorById(int directorId);

    List<Director> getDirectorsList();

    List<Director> getDirectorListForFilm(int directorId);

    void deleteDirector(int directorId);

    void deleteDirectorForFilm(int filmId);
}