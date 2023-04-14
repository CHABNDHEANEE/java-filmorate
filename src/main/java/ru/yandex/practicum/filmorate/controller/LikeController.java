package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.LikeService;

import java.util.List;

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
}
