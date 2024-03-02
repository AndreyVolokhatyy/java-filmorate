package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@Slf4j
public class FilmController {

    private FilmStorage filmStorage;
    private FilmService filmService;

    @Autowired
    public FilmController(InMemoryFilmStorage inMemoryFilmStorage, FilmService filmService) {
        filmStorage = inMemoryFilmStorage;
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<Film> listFilms() {
        return filmStorage.getListFilms();
    }

    @GetMapping("/films/{id}")
    public Film listFilms(@PathVariable int id) {
        return filmStorage.getFilm(id);
    }

    @RequestMapping(value = "/films",
            method = {RequestMethod.PUT, RequestMethod.POST})
    public Film addFilm(@RequestBody @Valid Film film) {
        return filmStorage.handlerFilms(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public Film addLikeFilm(@PathVariable int id, @PathVariable int userId) {
        return filmService.addLikes(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public Film deleteLikeFilm(@PathVariable int id, @PathVariable int userId) {
        return filmService.deleteLikes(id, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> addLikeFilm(@RequestParam(required = false) Integer count) {
        if (count == null) {
            count = 10;
        }
        return filmService.getPopular(count);
    }
}
