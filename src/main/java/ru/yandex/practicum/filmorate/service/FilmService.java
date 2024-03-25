package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private FilmStorage filmStorage;

    @Autowired
    public FilmService(
            @Qualifier("dataBaseFilm") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> getListFilms() {
        return filmStorage.getListFilms();
    }

    public Film getFilm(int id) {
        return filmStorage.getFilm(id);
    }

    public Film handlerFilms(Film film) {
        return filmStorage.handlerFilms(film);
    }

    public Film addLikes(int filmId, int userId) {
        return filmStorage.addLike(filmId, userId);
    }

    public Film deleteLikes(int filmsId, int userId) {
        filmStorage.addLike(filmsId, userId);
        return getFilm(filmsId);
    }

    public List<Film> getPopular(int count) {
        return filmStorage.getListFilms().stream()
                .sorted(Comparator.comparingLong(Film::getRate).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
