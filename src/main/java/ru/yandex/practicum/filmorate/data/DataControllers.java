package ru.yandex.practicum.filmorate.data;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
public class DataControllers {

    private static Map<String, Film> films = new HashMap<>();
    private static Map<String, User> users = new HashMap<>();

    public static Map<String, Film> getFilms() {
        return films;
    }

    public static Map<String, User> getUsers() {
        return users;
    }
}
