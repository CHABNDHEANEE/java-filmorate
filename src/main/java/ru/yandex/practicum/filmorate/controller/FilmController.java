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
@RequestMapping("/films")
public class FilmController {
    private final FilmDbService filmService;

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        log.info("Get film by id controller");

        return filmService.getFilmById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        filmService.deleteFilm(id);
    }

    @GetMapping()
    public List<Film> getAllFilms() {
        log.info("get all films controller");

        return filmService.getFilmsList(10);
    }

    @GetMapping("/common")
    public List<Film> getCommonFilms(@RequestParam Integer userId, @RequestParam Integer friendId) {
        return filmService.getCommonFilms(userId, friendId);
    }

    @GetMapping("/director/{directorId}")
    public List<Film> getFilmWithDirector(@PathVariable("directorId") int directorId, @RequestParam("sortBy")
    String value) {
        return filmService.getFilmWithDirector(directorId, value);
    }

    @GetMapping("/search")
    public List<Film> searchFilm(@RequestParam String query,
                                 @RequestParam String by) {
        log.info("films search by " + by);
        return filmService.searchFilm(query, by);
    }
}
