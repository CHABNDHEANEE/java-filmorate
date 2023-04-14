package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.FilmRating;
import ru.yandex.practicum.filmorate.service.RatingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Component
@RequestMapping("/mpa")
public class RatingController {
    private final RatingService ratingService;

    @GetMapping
    public List<FilmRating> getRatingList() {
        return ratingService.getRatingList();
    }

    @GetMapping("/{id}")
    public FilmRating getRatingById(@PathVariable int id) {
        return ratingService.getRatingById(id);
    }
}
