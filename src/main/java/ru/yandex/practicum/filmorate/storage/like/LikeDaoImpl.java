package ru.yandex.practicum.filmorate.storage.like;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
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
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeLike(rs), id);
    }

    public void delete(Integer filmId, Integer userId) {
        String sqlGenre = "update \"like\" " +
                "set \"is_active\" = false " +
                "where \"film_id\" = ? " +
                "and \"user_id\" = ?";
        jdbcTemplate.update(
                sqlGenre,
                filmId,
                userId
        );
    }

    public void insert(Integer filmId, Integer userId) {
        String sql = "INSERT INTO \"like\" " +
                "(\"film_id\", \"user_id\", \"is_active\")" +
                "VALUES (?, ?, true);";
        jdbcTemplate.update(
                sql,
                filmId,
                userId
        );
    }

    private Like makeLike(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int filmId = rs.getInt("film_id");
        int user_id = rs.getInt("user_id");
        boolean isActive = rs.getBoolean("is_active");
        return new Like(id, filmId, user_id, isActive);
    }
}
