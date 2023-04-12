package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Friends {
    private int userId;
    private int friendId;
    private int friendshipStatusId;
}
