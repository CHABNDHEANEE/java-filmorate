package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Service
@Qualifier
@RequiredArgsConstructor
public class FilmDbService {
    private final FilmDao filmDao;

    public Film addFilm(Film film) {
        return filmDao.addFilm(film);
    }

    public List<Film> getFilmsList(int max) {
        return filmDao.getFilmsList(max);
    }

    public Film getFilmById(int filmId) {
        return filmDao.getFilmById(filmId);
    }

    public Film updateFilm(Film film) {
        return filmDao.updateFilm(film);
    }
}
