package ru.yandex.practicum.filmorate.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
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
    @JsonIgnore
    private Set<Integer> friendsIdList = new HashSet<>();

    public void addFriend(User user) {
        if (friendsIdList == null) {
            friendsIdList = new HashSet<>();
        }

        friendsIdList.add(user.getId());
    }

    public void deleteFriend(User user) {
        friendsIdList.remove(user.getId());
    }
}