package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreStorage genreStorage;

    public List<Genre> getListGenre() {
        return genreStorage.findAllGenres();
    }

    public Genre getGenre(int id) {
        return genreStorage.findGenreById(id);
    }
}
