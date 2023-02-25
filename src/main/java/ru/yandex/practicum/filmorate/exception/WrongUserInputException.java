package ru.yandex.practicum.filmorate.exception;

public class WrongUserInputException extends RuntimeException {
    public WrongUserInputException(String msg) {
        super(msg);
    }
}
