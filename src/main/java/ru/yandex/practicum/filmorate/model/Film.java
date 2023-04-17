package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.auxilary.IsAfter;

import java.time.LocalDate;
import java.util.List;


import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@Builder
public class Film {
    @Builder.Default
    private int id = 1;
    @NotBlank
    private String name;
    private List<FilmGenre> genres;
    @NotBlank
    @Size(max = 200)
    private String description;
    @NotNull
    @IsAfter(current = "1895-12-28")
    private LocalDate releaseDate;
    @NotNull
    @Min(value = 0)
    private int duration;
    @NotNull
    private FilmRating mpa;

    public void setMpa(FilmRating mpa) {
        this.mpa = mpa;
    }
}
