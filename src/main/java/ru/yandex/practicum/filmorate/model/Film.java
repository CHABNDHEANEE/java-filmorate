package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
public class Film {
    @Builder.Default
    private int id = 0;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @NonNull
    private int duration;
}
