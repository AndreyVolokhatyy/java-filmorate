package ru.yandex.practicum.filmorate.storage.user;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.friend.FriendDaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component("dataBaseUser")
@Data
public class UserDbStorage implements UserStorage {

    private FriendDaoImpl friendDao;
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate, @Autowired FriendDaoImpl friendDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.friendDao = friendDao;
    }

    @Override
    public Map<Integer, User> getUsers() {
        return null;
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public List<User> getListUsers() {
        String sql = "select * from \"user\" where \"is_active\"";
        return jdbcTemplate.query(sql, (rs, rowNum) -> userResponse(rs));
    }

    @Override
    public User getUser(int id) {
        return jdbcTemplate.queryForObject("select * from \"user\" where \"id\" = ? and \"is_active\"",
                (rs, rowNum) -> userResponse(rs), id);
    }

    @Override
    public User heandlerUser(User user) {
        if (user.getId() != 0) {
            updater(user);
        } else {
            createNewUser(user);
        }
        System.out.println(getUserFromLogin(user.getLogin()));
        return getUserFromLogin(user.getLogin());
    }

    @Override
    public void addFriend(int id, int otherId) {
        friendDao.addFriends(id, otherId);
    }

    @Override
    public void deleteFriend(int id, int otherId) {
        friendDao.deleteFriends(id, otherId);
    }

    private User userResponse(ResultSet rs) throws SQLException {

        int id = rs.getInt("id");
        String login = rs.getString("login");
        String name = rs.getString("name");
        String email = rs.getString("email");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        boolean isActive = rs.getBoolean("is_active");
        Set<Friend> friend = new HashSet<>(friendDao.findAllFriends(id));
        return new User(id, login, name, email, birthday, friend, isActive);
    }

    private User createNewUser(User user) {
        String sqlUser = "INSERT INTO \"user\" " +
                "(\"login\", \"name\", \"email\", \"birthday\", \"is_active\")" +
                "VALUES (?, ?, ?, ?, true);";
        jdbcTemplate.update(sqlUser,
                user.getLogin(),
                user.getName(),
                user.getEmail(),
                user.getBirthday()
        );
        return getUserFromLogin(user.getLogin());
    }

    @Override
    public User updater(User user) {
        String sqlFilm = "update \"user\" " +
                "set \"login\" = ?, " +
                "\"name\" = ?, " +
                "\"email\" = ?, " +
                "\"birthday\" = ? " +
                " where \"id\" = ?;";
        jdbcTemplate.update(
                sqlFilm,
                user.getLogin(),
                user.getName(),
                user.getEmail(),
                user.getBirthday(),
                user.getId()
        );
        return user;
    }

    private User getUserFromLogin(String login) {
        return jdbcTemplate.queryForObject("select * from \"user\" where \"login\" = ? and \"is_active\"",
                (rs, rowNum) -> userResponse(rs), login);
    }

}
