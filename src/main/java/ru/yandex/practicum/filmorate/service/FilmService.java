package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private FilmStorage filmStorage;
    private UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
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

    public Film addLikes(int filmsId, int userId) {
        Map<Integer, Film> films = filmStorage.getFilms();
        Map<Integer, User> users = userStorage.getUsers();
        Film film = films.get(filmsId);
        Set<Integer> temp = film.getLikes();
        temp.add(users.get(userId).getId());
        film.setRate(temp.size());
        return film;
    }

    public Film deleteLikes(int filmsId, int userId) {
        Map<Integer, Film> films = filmStorage.getFilms();
        Map<Integer, User> users = userStorage.getUsers();
        Film film = films.get(filmsId);
        Set<Integer> temp = film.getLikes();
        temp.remove(users.get(userId).getId());
        film.setRate(temp.size());
        return film;
    }

    public Set<Integer> getLikes(int filmId) {
        Map<Integer, Film> films = filmStorage.getFilms();
        Film user = films.get(filmId);
        return user.getLikes();
    }

    public List<Film> getPopular(int count) {
        return filmStorage.getListFilms().stream()
                .sorted(Comparator.comparingLong(Film::getRate))
                .limit(count)
                .collect(Collectors.toList());
    }
}
