package ru.yandex.practicum.filmorate.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Builder.Default
    private Set<User> friendsList = new HashSet<>();

    public void addFriend(User user) {
        friendsList.add(user);
    }

    public void deleteFriend(User user) {
        friendsList.remove(user);
    }
}