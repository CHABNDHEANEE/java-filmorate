package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmsListException;
import ru.yandex.practicum.filmorate.exception.WrongFilmException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    private int id = 1;
    private Map<Integer, Film> films = new HashMap<>();

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        checkFilm(film);
        setIdForFilm(film);
        films.put(film.getId(), film);
        log.info("Film added");
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        int id = film.getId();

        checkFilm(film);
        if (!films.containsKey(id)) {
            log.info("id doesn't exist");
            throw new WrongFilmException("Film with the id doesn't exist");
        }
        films.put(id, film);
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
        if (film.getDescription().length() > 200) {
            log.info("Too long desc err");
            //noinspection SpellCheckingInspection
            throw new WrongFilmException("The description is tooooooooo long");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.info("Wrong release date err");
            throw new WrongFilmException("The release date can't be before 1895.12.28");
        }
        if (film.getDuration() < 0) {
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
        id = 1;
    }

    private void setIdForFilm(Film film) {
        film.setId(id);
        id++;
    }
}
