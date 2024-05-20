package ru.yandex.practicum.filmorate.storage.rate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.SQLRequestExceptions;
import ru.yandex.practicum.filmorate.model.RateMPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class RateMPADaoImpl implements RateMPAStorage {

    private final JdbcTemplate jdbcTemplate;

    public RateMPADaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<RateMPA> findRates() {
        String sql = "select * from \"rate_mpa\" where \"is_active\"";
        try {
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeRateMPA(rs));
        } catch (Exception e) {
            throw new SQLRequestExceptions(e);
        }
    }

    @Override
    public RateMPA findRateById(int id) {
        String sql = "select * from \"rate_mpa\" where \"id\"=? and \"is_active\"";
        try {
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeRateMPA(rs), id);
        } catch (Exception e) {
            throw new SQLRequestExceptions(e);
        }
    }

    private RateMPA makeRateMPA(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        boolean isActive = rs.getBoolean("is_active");
        return new RateMPA(id, name, isActive);
    }
}
