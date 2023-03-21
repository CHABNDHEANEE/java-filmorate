package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ObjectExistenceException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@Component
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

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

        return filmService.getAllFilms();
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void likeFilm(@PathVariable int id, @PathVariable int userId) {
        log.info("like film controller");

        filmService.like(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void unlikeFilm(@PathVariable int id, @PathVariable int userId) {
        log.info("unlike film controller");

        filmService.unlike(id, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getBestFilms(@RequestParam(required = false) Integer count) {
        log.info("get best films.");

        return filmService.getMostPopularFilms(count);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions( final
            MethodArgumentNotValidException e) {
        return Map.of("Validation error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final ValidationException e) {
        return Map.of("Validation error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleObjectExistenceException(final ObjectExistenceException e) {
        return Map.of("Object doesn't found", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleRuntimeError(final Exception e) {
        return Map.of("Server error!", e.getMessage());
    }

    public void clearFilmsList() {
        filmService.clearFilmsList();
    }
}
