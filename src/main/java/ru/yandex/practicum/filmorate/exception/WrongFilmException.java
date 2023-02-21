package ru.yandex.practicum.filmorate.exception;

public class WrongFilmException extends RuntimeException {
    public WrongFilmException(String msg) {
        super(msg);
    }
}
