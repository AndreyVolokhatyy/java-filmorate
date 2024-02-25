package ru.yandex.practicum.filmorate.validators;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
