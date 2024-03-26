package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@Slf4j
public class FilmController {

    private static final int DEFAULT_LIKE = 10;

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<Film> listFilms() {
        return filmService.getListFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilms(@PathVariable int id) {
        return filmService.getFilm(id);
    }

    @RequestMapping(value = "/films",
            method = {RequestMethod.PUT, RequestMethod.POST})
    public Film addFilm(@RequestBody @Valid Film film) {
        return filmService.handlerFilms(film);
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
            count = DEFAULT_LIKE;
        }
        return filmService.getPopular(count);
    }
}
