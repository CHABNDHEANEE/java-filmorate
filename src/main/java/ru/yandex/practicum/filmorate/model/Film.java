package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;



import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class Film {
    @Builder.Default
    private int id = 0;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NonNull
    private LocalDate releaseDate;
    @NonNull
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
