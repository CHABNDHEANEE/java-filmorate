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

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        return ;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        return ;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return ;
    }

    public void clearFilmsList() {

    }
}
