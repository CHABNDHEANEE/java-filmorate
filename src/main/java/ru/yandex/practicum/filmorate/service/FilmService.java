package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;

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

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public void like(int filmId, int userId) {
        log.info("like film service");

        Film film = filmStorage.getFilm(filmId);
        User user = userService.getUser(userId);

        film.like(user);
    }

    public void unlike(int filmId, int userId) {
        log.info("unlike film service");

        Film film = filmStorage.getFilm(filmId);
        User user = userService.getUser(userId);

        film.unlike(user);
    }

    public List<Film> getMostPopularFilms(Optional<Integer> count) {
        int filmsToGet = 10;
        if (count.isPresent()) {
            filmsToGet = count.get();
        }
        return filmStorage.getAllFilms().stream().sorted(Comparator.comparing(Film::getLikes)).limit(filmsToGet).collect(Collectors.toList());
    }


    public void clearFilmsList() {
        filmStorage.clearFilmsList();
    }
}
