package ru.yandex.practicum.filmorate.storage.film.genre;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Component
public class FilmGenreDaoImpl implements FilmGenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public FilmGenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<FilmGenre> findGenreById(int id) {
        String sql = "select * from \"film_genre\" where \"film_id\"=? and \"is_active\"";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilmGenre(rs), id);
    }

    @Override
    public void insert(Set<Genre> genres, int id) {
        String sqlGenre = "INSERT INTO \"film_genre\" " +
                "(\"film_id\", \"genre_id\", \"is_active\")" +
                "VALUES (?, ?, true);";
        genres.forEach(g -> jdbcTemplate.update(
                        sqlGenre,
                        id,
                        g.getId()
                )
        );
    }

    @Override
    public void delete(Film film) {
        String sqlGenre = "update \"film_genre\" " +
                "set \"is_active\" = false " +
                "where \"film_id\" = ?";
        film.getGenres().stream().forEach(g -> jdbcTemplate.update(
                sqlGenre,
                film.getId())
        );
    }

    private FilmGenre makeFilmGenre(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int filmId = rs.getInt("film_id");
        int genreId = rs.getInt("genre_id");
        boolean isActive = rs.getBoolean("is_active");
        return new FilmGenre(id, filmId, genreId, isActive);
    }
}
