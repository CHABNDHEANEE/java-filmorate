package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmDbService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@Component
@RequiredArgsConstructor
public class FilmController {
    private final FilmDbService filmService;

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable int id) {
        log.info("Get film by id controller");

        return filmService.getFilmById(id);
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        log.info("get all films controller");

        return filmService.getFilmsList(10);
    }
}
