package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmsListException;
import ru.yandex.practicum.filmorate.exception.WrongFilmException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    private HashMap<Integer, Film> films = new HashMap<>();

    @PostMapping("/film")
    public Film addFilm(@RequestBody Film film) {
        checkFilm(film);
        films.put(film.getId(), film);
        log.info("Film added");
        return film;
    }

    @PutMapping("/film")
    public Film updateFilm(@RequestBody Film film) {
        checkFilm(film);
        films.put(film.getId(), film);
        log.info("Film updated");
        return film;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        checkFilmsExistence();
        log.info("Get all films");
        return new ArrayList<>(films.values());
    }

    private void checkFilm(Film film) {
        if (film.getName().isBlank()) {
            log.info("Blank name err");
            throw new WrongFilmException("The name can't be blank!");
        }
        if (film.getName().length() > 200) {
            log.info("Too long name err");
            //noinspection SpellCheckingInspection
            throw new WrongFilmException("The name is tooooooooo long");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.info("Wrong release date err");
            throw new WrongFilmException("The release date can't be before 1895.12.28");
        }
        if (film.getDuration().isNegative()) {
            log.info("Neg duration err");
            throw new WrongFilmException("The duration can't be negative");
        }
    }

    private void checkFilmsExistence() {
        if (films.values().isEmpty()) {
            throw new FilmsListException("Список фильмов пуст!");
        }
    }

    public void clearFilmsList() {
        films = new HashMap<>();
    }
}
