package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmStorage {

    Map<Integer, Film> getFilms();

    Film handlerFilms(Film film);

    void deleteFilm(Film film);

    Film updater(Film film);

    List<Film> getListFilms();

    Film getFilm(int id);

    Film addLike(Integer id, Integer userId);

    Film deleteLike(Integer id, Integer userId);
}
