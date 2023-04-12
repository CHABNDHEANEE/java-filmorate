package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class FilmRating {
    @NotNull
    private int Id;
    private String ratingName;

    public FilmRating(int ratingId) {
        this.Id = ratingId;
    }
}
