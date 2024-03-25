package ru.yandex.practicum.filmorate.storage.friend;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.SQLRequestExceptions;
import ru.yandex.practicum.filmorate.model.Friend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FriendDaoImpl implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    public FriendDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Friend> findAllFriends(Integer id) {
        String sql = "select * from \"friend\" where \"following_user_id\" = ? and \"is_active\" order by \"id\"";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), id);
        } catch (Exception e) {
            throw new SQLRequestExceptions(e);
        }
    }

    @Override
    public void deleteFriends(Integer followingId, Integer followedId) {
        String sql = "update \"friend\" " +
                "set \"is_active\" = false " +
                "where \"following_user_id\" = ? and \"followed_user_id\" = ?";
        try {
            jdbcTemplate.update(sql, followingId, followedId);
        } catch (Exception e) {
            throw new SQLRequestExceptions(e);
        }
    }

    @Override
    public void addFriends(Integer followingId, Integer followedId) {
        String sql = "INSERT INTO \"friend\" " +
                "(\"following_user_id\", \"followed_user_id\", \"is_accept\", \"is_active\")" +
                "VALUES (?, ?, false, true);";
        try {
            jdbcTemplate.update(sql, followingId, followedId);
        } catch (Exception e) {
            throw new SQLRequestExceptions(e);
        }
    }

    private Friend makeGenre(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int following = rs.getInt("following_user_id");
        int followed = rs.getInt("followed_user_id");
        boolean isAccept = rs.getBoolean("is_accept");
        boolean isActive = rs.getBoolean("is_active");
        return new Friend(id, following, followed, isAccept, isActive);
    }
}
