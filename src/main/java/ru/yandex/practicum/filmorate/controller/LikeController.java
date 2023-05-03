package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.LikeService;

import java.util.List;

@RestController
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
    public List<Film> getPopularFilms(@RequestParam(required = false) Integer genreId,
                                      @RequestParam(name = "count", required = false, defaultValue = "10") Integer limit,
                                      @RequestParam(required = false) String year) {
        return likeService.getPopularFilms(limit, genreId, year);
    }
}
