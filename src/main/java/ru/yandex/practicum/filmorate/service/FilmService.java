package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;

    public Film addFilm(Film film) {
        log.info("add film");
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        log.info("update film");
        return filmStorage.updateFilm(film);
    }

    public Film deleteFilm(Film film) {
        log.info("delete film");
        return filmStorage.deleteFilm(film);
    }

    public Film getFilmById(int id) {
        return filmStorage.getFilm(id);
    }

    public void like(Film film, User user) {
        film.like(user);
    }

    public void unlike(Film film, User user) {
        film.unlike(user);
    }

    public List<Film> getMostPopularFilms() {
        return filmStorage.getAllFilms().stream().sorted(Comparator.comparing(Film::getLikes)).limit(10).collect(Collectors.toList());
    }


    public void clearFilmsList() {
        filmStorage.clearFilmsList();
    }
}
