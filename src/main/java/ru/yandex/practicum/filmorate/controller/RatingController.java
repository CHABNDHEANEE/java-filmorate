package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ObjectExistenceException;
import ru.yandex.practicum.filmorate.model.FilmRating;
import ru.yandex.practicum.filmorate.service.RatingService;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Map;

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
