package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.dao.RatingDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

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
}
