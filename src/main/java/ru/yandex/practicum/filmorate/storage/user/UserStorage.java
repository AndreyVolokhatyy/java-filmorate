package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface UserStorage {

    Map<Integer, User> getUsers();

    void deleteUser(User user);

    User updater(User user);

    List<User> getListUsers();

    User getUser(int id);

    User heandlerUser(User user);

    void addFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);
}
