package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Map;

public interface FilmStorage {

    Map<Integer, Film> getFilms();

    Film handlerFilms(Film film);

    void deleteFilm(Film film);

    Film updater(Film film);

    ArrayList<Film> getListFilms();

    Film getFilm(int id);
}
