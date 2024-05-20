package ru.yandex.practicum.filmorate.storage.film.genre;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

public interface FilmGenreStorage {

    List<FilmGenre> findGenreById(int id);

    void insert(Set<Genre> genres, int id);

    void delete(Film film);
}
