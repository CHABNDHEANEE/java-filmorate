package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    @Builder.Default
    private int id = -1;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String login;
    private String name;
    @NotNull
    private LocalDate birthday;
}
