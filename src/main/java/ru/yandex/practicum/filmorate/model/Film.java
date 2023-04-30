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
    private List<Director> directors;
//
//    public Film(int id, String name, List<FilmGenre> genres, String description, LocalDate releaseDate, int duration, FilmRating mpa) {
//        this.id = id;
//        this.name = name;
//        this.genres = genres;
//        this.description = description;
//        this.releaseDate = releaseDate;
//        this.duration = duration;
//        this.mpa = mpa;
//    }

    public void removeDirector() {
       directors.clear();
    }
}
