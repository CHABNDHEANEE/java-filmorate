package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class FilmRating {
    @NotNull
    private int id;
    private String name;

    public FilmRating(int ratingId) {
        this.id = ratingId;
    }
}
