package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder
public class Film {
    @Builder.Default
    private int id = 1;
    @NotBlank
    private String title;
    @NotNull
    private int genreId;
    @NotBlank
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @NotNull
    private int duration;
    @NotNull
    private int ratingId;
}
