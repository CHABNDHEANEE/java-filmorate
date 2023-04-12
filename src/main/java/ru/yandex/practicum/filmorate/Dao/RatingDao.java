package ru.yandex.practicum.filmorate.Dao;

import ru.yandex.practicum.filmorate.model.FilmRating;

import java.util.List;

public interface RatingDao {
    List<FilmRating> getRatingList();

    FilmRating getRatingById(int ratingId);
}
