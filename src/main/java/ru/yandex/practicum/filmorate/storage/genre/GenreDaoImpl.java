package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.SQLRequestExceptions;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class GenreDaoImpl implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> findAllGenres() {
        String sql = "select * from \"genre\" order by \"id\" and \"is_active\"";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
        } catch (Exception e) {
            throw new SQLRequestExceptions(e);
        }
    }

    @Override
    public Genre findGenreById(int id) {
        String sql = "select * from \"genre\" where \"id\"=? and \"is_active\"";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeGenre(rs), id);
        } catch (Exception e) {
            throw new SQLRequestExceptions(e);
        }
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        boolean isActive = rs.getBoolean("is_active");
        return new Genre(id, name, isActive);
    }
}
