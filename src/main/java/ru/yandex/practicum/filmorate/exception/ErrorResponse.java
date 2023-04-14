package ru.yandex.practicum.filmorate.exception;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ErrorResponse {
    private String msg;
    private String details;
}
