package ru.yandex.practicum.filmorate.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class User {
    @Builder.Default
    private int id = 1;
    @NotBlank
    private String email;
    @NotBlank
    private String login;
    @NotBlank
    private String name;
    @NotNull
    private LocalDate birthday;

}