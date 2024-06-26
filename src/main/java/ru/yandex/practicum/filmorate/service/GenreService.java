package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;

@Service
public class GenreService {

    private GenreStorage genreStorage;

    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public List<Genre> getListGenre() {
        return genreStorage.findAllGenres();
    }

    public Genre getGenre(int id) {
        return genreStorage.findGenreById(id);
    }
}
