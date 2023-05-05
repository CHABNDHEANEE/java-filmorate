package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.dao.RatingDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@Qualifier
@RequiredArgsConstructor
public class FilmDbService {
    private final FilmDao filmDao;
    private final RatingDao ratingDao;
    private final GenreDao genreDao;

    public Film addFilm(Film film) {
        film.setMpa(ratingDao.getRatingById(film.getMpa().getId()));
        return filmDao.addFilm(film);
    }

    public List<Film> getFilmsList(int max) {
        return filmDao.getFilmsList(max);
    }

    public Film getFilmById(int filmId) {
        return filmDao.getFilmById(filmId);
    }

    public Film updateFilm(Film film) {
        film.setMpa(ratingDao.getRatingById(film.getMpa().getId()));
        return filmDao.updateFilm(film);
    }

    public void deleteFilm(int filmId) {
        filmDao.deleteFilm(filmId);
    }

    public List<Film> getFilmWithDirectorSortByYear(int directorId) {
        return filmDao.getFilmWithDirectorSortByYear(directorId);
    }

    public List<Film> getFilmWithDirectorSortByLikes(int directorId) {
        return filmDao.getFilmWithDirectorSortByLikes(directorId);
    }

    public List<Film> getCommonFilms(Integer userId, Integer friendId) {
        log.info("Get common films between user id='{}' and friend id='{}'", userId, friendId);
        return filmDao.findCommonFilms(userId, friendId);
    }

    public List<Film> searchFilm(String query, String by) {
        List<String> list = new ArrayList<>(Arrays.asList(by.split(",")));
        if (list.size() == 2) {
            return filmDao.getFilmsSearchByDirectorAndTitle(query);
        }
        if (list.get(0).equalsIgnoreCase("title")) {
            return filmDao.getFilmsSearchByTitle(query);
        }
        if (list.get(0).equalsIgnoreCase("director")) {
            return filmDao.getFilmsSearchByDirector(query);
        } else
            throw new IllegalArgumentException("unexpected param <by> - " + by);
    }
}