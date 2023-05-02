package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@Builder
public class Feed {
    @NotNull
    private int eventId;
    @NotNull
    private int userId;
    @NotNull
    private int entityId;
    @NotNull
    private long timestamp;
    @NotBlank
    private EventType eventType;
    @NotBlank
    private Operation operation;
}
