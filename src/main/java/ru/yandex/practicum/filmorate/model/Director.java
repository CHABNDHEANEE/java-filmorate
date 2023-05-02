package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Builder
public class Director {
    @Builder.Default
    private int id = 1;
    @NotBlank
    @Size(max = 50)
    private String name;

    public Director(int id) {
        this.id = id;
    }
}
