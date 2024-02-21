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

    private static Map<Integer, Film> films = new HashMap<>();
    private static Map<Integer, User> users = new HashMap<>();

    public static Map<Integer, Film> getFilms() {
        return films;
    }

    public static Map<Integer, User> getUsers() {
        return users;
    }

    public static void clearData() {
        films.clear();
        users.clear();
    }
}
