package ru.yandex.practicum.filmorate.storage.film;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component("inMemory")
@Data
public class InMemoryFilmStorage implements FilmStorage {

    private Map<Integer, Film> films;

    private int maxId;

    public InMemoryFilmStorage() {
        films = new HashMap<>();
    }

    @Override
    public void deleteFilm(Film film) {
        films.remove(film.getId());
    }

    @Override
    public Film updater(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    public ArrayList<Film> getListFilms() {
        return new ArrayList<>(films.values());
    }

    public Film getFilm(int id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Film addLike(Integer id, Integer userId) {
        return null;
    }

    @Override
    public Film deleteLike(Integer id, Integer userId) {
        return null;
    }

    public Film handlerFilms(Film film) {
        boolean skip = checkName(film.getName());
        if (!films.containsKey(film.getId()) && !skip) {
            maxId++;
            film.setId(maxId);
            return updater(film);
        }
        if (films.containsKey(film.getId())) {
            return updater(film);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean checkName(String str) {
        boolean skip = false;
        for (Film f : films.values()) {
            if (f.getName().equals(str)) {
                skip = true;
            }
        }
        return skip;
    }
}
