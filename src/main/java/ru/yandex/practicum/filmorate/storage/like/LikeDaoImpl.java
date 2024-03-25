package ru.yandex.practicum.filmorate.storage.like;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.SQLRequestExceptions;
import ru.yandex.practicum.filmorate.model.Like;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class LikeDaoImpl implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;

    public LikeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Like> findLikeByFilmId(int id) {
        String sql = "select * from \"like\" where \"film_id\"=? and \"is_active\"";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> makeLike(rs), id);
        } catch (Exception e) {
            throw new SQLRequestExceptions(e);
        }
    }

    public void delete(Integer filmId, Integer userId) {
        String sqlGenre = "update \"like\" " +
                "set \"is_active\" = false " +
                "where \"film_id\" = ? " +
                "and \"user_id\" = ?";
        try {
            jdbcTemplate.update(
                    sqlGenre,
                    filmId,
                    userId
            );
        } catch (Exception e) {
            throw new SQLRequestExceptions(e);
        }
    }

    public void insert(Integer filmId, Integer userId) {
        String sql = "INSERT INTO \"like\" " +
                "(\"film_id\", \"user_id\", \"is_active\")" +
                "VALUES (?, ?, true);";
        try {
            jdbcTemplate.update(
                    sql,
                    filmId,
                    userId
            );
        } catch (Exception e) {
            throw new SQLRequestExceptions(e);
        }
    }

    private Like makeLike(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int filmId = rs.getInt("film_id");
        int userId = rs.getInt("user_id");
        boolean isActive = rs.getBoolean("is_active");
        return new Like(id, filmId, userId, isActive);
    }
}
