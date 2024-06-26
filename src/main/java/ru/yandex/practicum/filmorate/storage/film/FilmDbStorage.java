package ru.yandex.practicum.filmorate.storage.film;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.BadRequestException;
import ru.yandex.practicum.filmorate.exceptions.SQLRequestExceptions;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.genre.GenreDaoImpl;
import ru.yandex.practicum.filmorate.storage.film.genre.FilmGenreDaoImpl;
import ru.yandex.practicum.filmorate.storage.like.LikeDaoImpl;
import ru.yandex.practicum.filmorate.storage.rate.RateMPADaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component("dataBaseFilm")
@Data
public class FilmDbStorage implements FilmStorage {

    private FilmGenreDaoImpl filmGenreDao;
    private GenreDaoImpl genreDao;
    private RateMPADaoImpl rateMPADao;
    private LikeDaoImpl likeDao;
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(FilmGenreDaoImpl filmGenreDao,
                         GenreDaoImpl genreDao,
                         RateMPADaoImpl rateMPADao,
                         LikeDaoImpl likeDao,
                         JdbcTemplate jdbcTemplate) {
        this.filmGenreDao = filmGenreDao;
        this.genreDao = genreDao;
        this.rateMPADao = rateMPADao;
        this.likeDao = likeDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<Integer, Film> getFilms() {
        return null;
    }

    @Override
    public Film handlerFilms(Film film) {
        if (film.getId() != 0) {
            updater(film);
            return getFilm(film.getId());
        } else {
            createNewFilm(film);
            return getFilmFromName(film.getName());
        }
    }

    @Override
    public void deleteFilm(Film film) {
        String sql = "update \"film\" set \"is_active\" = false where \"id\" = ?";
        try {
            jdbcTemplate.update(sql, film.getId());
        } catch (Exception e) {
            throw new SQLRequestExceptions(e);
        }
    }

    @Override
    public Film updater(Film film) {
        String sqlFilm = "update \"film\" " +
                "set \"name\" = ?, " +
                "\"description\" = ?, " +
                "\"release_date\" = ?, " +
                "\"duration\" = ?, " +
                "\"rate_mpa_id\" = ? " +
                " where \"id\" = ?;";
        try {
            jdbcTemplate.update(
                    sqlFilm,
                    film.getName(),
                    film.getDescription(),
                    film.getReleaseDate(),
                    film.getDuration(),
                    film.getMpa().getId(),
                    film.getId()
            );

        } catch (Exception e) {
            throw new SQLRequestExceptions(e);
        }
        filmGenreDao.delete(film);
        filmGenreDao.insert(film.getGenres(), film.getId());
        return film;
    }

    @Override
    public List<Film> getListFilms() {
        String sql = "select * from \"film\" where \"is_active\"";
        return jdbcTemplate.query(sql, (rs, rowNum) -> filmResponse(rs));
    }

    @Override
    public Film getFilm(int id) {
        String sql = "select * from \"film\" where \"id\" = ? and \"is_active\"";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> filmResponse(rs), id);
        } catch (Exception e) {
            throw new SQLRequestExceptions(e);
        }
    }

    @Override
    public Film addLike(Integer id, Integer userId) {
        likeDao.insert(id, userId);
        return getFilm(id);
    }

    @Override
    public Film deleteLike(Integer id, Integer userId) {
        likeDao.delete(id, userId);
        return getFilm(id);
    }

    private Film filmResponse(ResultSet rs) throws SQLException {
        Set<Integer> like = new HashSet<>();
        SortedSet<Genre> genre = new TreeSet<>(Comparator.comparingInt(Genre::getId));

        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int duration = rs.getInt("duration");
        RateMPA mpa = rateMPADao.findRateById(rs.getInt("rate_mpa_id"));
        boolean isActive = rs.getBoolean("is_active");

        List<FilmGenre> genres = filmGenreDao.findGenreById(id);
        for (FilmGenre f : genres) {
            genre.add(genreDao.findGenreById(f.getGenreId()));
        }

        List<Like> likes = likeDao.findLikeByFilmId(id);
        for (Like l : likes) {
            like.add(l.getId());
        }

        return new Film(id, name, description, genre, releaseDate, duration, like, mpa, like.size(), isActive);
    }

    private Film getFilmFromName(String name) {
        return jdbcTemplate.queryForObject("select * from \"film\" where \"name\" = ? and \"is_active\" order by \"id\" desc limit 1",
                (rs, rowNum) -> filmResponse(rs), name);
    }

    private Film createNewFilm(Film film) {
        String sqlFilm = "INSERT INTO \"film\" " +
                "(\"name\", \"description\", \"release_date\", \"duration\", \"rate_mpa_id\", \"is_active\")" +
                "VALUES (?, ?, ?, ?, ?, true);";
        try {
            rateMPADao.findRateById(film.getMpa().getId());
        } catch (Exception e) {
            throw new BadRequestException(e);
        }
        jdbcTemplate.update(sqlFilm,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId()
        );
        filmGenreDao.insert(film.getGenres(), getFilmFromName(film.getName()).getId());
        return getFilmFromName(film.getName());
    }
}
