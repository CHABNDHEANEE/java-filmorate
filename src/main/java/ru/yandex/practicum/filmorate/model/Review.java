package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder
public class Review {
    @NotNull
    private int reviewId;
    @NotBlank
    private String content;
    @NotNull
    private boolean isPositive;
    @NotNull
    private int userId;
    @NotNull
    private int filmId;
    @Builder.Default
    private int useful = 0;
}
