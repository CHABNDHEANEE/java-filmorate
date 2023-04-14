package ru.yandex.practicum.filmorate.exception;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse exceptionResponse = new ErrorResponse("Validation error", ex.getLocalizedMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final ValidationException e) {
        return Map.of("Validation error", e.getMessage());
    }

    @ExceptionHandler(ObjectExistenceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleObjectExistenceException(final ObjectExistenceException e) {
        return Map.of("Object doesn't found", e.getMessage());
    }

    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleIncorrectResultSizeDataAccessException(final IncorrectResultSizeDataAccessException e) {
        return Map.of("Object doesn't found", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleRuntimeError(final Exception e) {
        return Map.of("Server error!", e.getMessage());
    }

}
