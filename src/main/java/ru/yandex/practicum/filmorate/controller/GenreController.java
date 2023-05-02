package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public List<FilmGenre> getGenresList() {
        return genreService.getGenresList();
    }

    @GetMapping("/{id}")
    public FilmGenre getGenreById(@PathVariable int id) {
        return genreService.getGenreById(id);
    }
}
