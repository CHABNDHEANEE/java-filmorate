package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
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
    private String title;
    @NotBlank
    private int genreId;
    @NotBlank
    private String description;
    @NotNull
    private Timestamp releaseDate;
    @NotNull
    private int duration;
    @NotBlank
    private int ratingId;
    final private Set<Integer> userLiked = new HashSet<>();

    public void like(User user) {
        userLiked.add(user.getId());
    }

    public void unlike(User user) {
        userLiked.remove(user.getId());
    }

    public int getLikes() {
        return userLiked.size();
    }
}
