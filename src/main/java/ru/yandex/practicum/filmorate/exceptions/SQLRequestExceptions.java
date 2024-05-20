package ru.yandex.practicum.filmorate.exceptions;

public class SQLRequestExceptions extends RuntimeException {

    public SQLRequestExceptions(Exception e) {
        super(e);
    }
}