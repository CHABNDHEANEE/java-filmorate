package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private int duration;

    final private Set<User> userLiked = new HashSet<>();

    public void like(User user) {
        userLiked.add(user);
    }

    public void unlike(User user) {
        userLiked.remove(user);
    }

    public int getLikes() {
        return userLiked.size();
    }
}
