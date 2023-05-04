package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmDbService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
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
    public List<Film> getFilmWithDirectorSortByYear(@PathVariable("directorId") int directorId, @RequestParam("sortBy")
    String value) {
        if (value.equals("year")) {
            log.info("get Film With Director Sort By Year");
            List<Film> filmYear = filmService.getFilmWithDirectorSortByYear(directorId);
            for (Film filmY : filmYear) {
                if (filmY.getDirectors().size() == 0)
                    throw new IncorrectResultSizeDataAccessException("Фильмы отсутствуют", 0);
                else
                    return filmYear;
            }
        } else if (value.equals("likes")) {
            log.info("get Film With Director Sort By Likes");
            List<Film> filmLikes = filmService.getFilmWithDirectorSortByLikes(directorId);
            for (Film filmY : filmLikes) {
                if (filmY.getDirectors().size() == 0)
                    throw new IncorrectResultSizeDataAccessException("Фильмы отсутствуют", 0);
                else
                    return filmLikes;
            }
        }
        return null;
    }

    @GetMapping("/search")
    public List<Film> searchFilm(@RequestParam String query,
                                 @RequestParam String by) {
        log.info("films search by " + by);
        List<String> list = new ArrayList<>(Arrays.asList(by.split(",")));
        if (list.size() == 2) {
            return filmService.getFilmsSearchByDirectorAndTitle(query);
        }
        if (list.get(0).equalsIgnoreCase("title")) {
            return filmService.getFilmsSearchByTitle(query);
        }
        if (list.get(0).equalsIgnoreCase("director")) {
            return filmService.getFilmsSearchByDirector(query);
        } else
            throw new IllegalArgumentException("unexpected param <by> - " + by);
    }
}
