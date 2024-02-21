package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.data.DataControllers;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@Slf4j
public class FilmController {


    private Map<String, Film> films = DataControllers.getFilms();
    private int maxId;

    @GetMapping("/films")
    public List<Film> listFilms() {
        return new ArrayList<>(films.values());
    }

    @PutMapping("/films")
    public Film addFilm(@RequestBody @Valid Film film) {
        if (!films.containsKey(film.getName())) {
            if (maxId == 0) {
                maxId = 1;
                film.setId(1);
            } else {
                maxId++;
                film.setId(maxId);
            }
            films.put(film.getName(), film);
            return film;
        } else {
            return null;
        }
    }

    @PostMapping("/films")
    public Film updateFilm(@RequestBody Film film) {
        String name = film.getName();
        if (films.containsKey(name)) {
            film.setId(films.get(name).getId());
            films.put(film.getName(), film);
            return film;
        } else {
            log.info("Film not found");
            return null;
        }
    }
}
