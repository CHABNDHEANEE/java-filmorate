package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ObjectExistenceException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.LikeService;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Map;

@RestController
@Component
@RequiredArgsConstructor
@Slf4j
public class LikeController {
    private final LikeService likeService;

    @PutMapping("/films/{id}/like/{userId}")
    public void likeFilm(@PathVariable int id, @PathVariable int userId) {
        log.info("like film controller");

        likeService.like(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void unlikeFilm(@PathVariable int id, @PathVariable int userId) {
        log.info("unlike film controller");

        likeService.unlike(id, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getBestFilms(@RequestParam(required = false) Integer count) {
        log.info("get best films.");

        return likeService.getMostPopularFilms(count);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(final
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
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleIncorrectResultSizeDataAccessException(final IncorrectResultSizeDataAccessException e) {
        return Map.of("Object doesn't found", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleRuntimeError(final Exception e) {
        return Map.of("Server error!", e.getMessage());
    }

}
