package ru.yandex.practicum.filmorate.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class User {
    @Builder.Default
    private int id = 1;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String login;
    @NotNull
    private String name;
    @Past
    @NotNull
    private LocalDate birthday;
}