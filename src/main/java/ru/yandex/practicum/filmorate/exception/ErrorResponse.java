package ru.yandex.practicum.filmorate.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
    private String msg;
    private String details;
}
