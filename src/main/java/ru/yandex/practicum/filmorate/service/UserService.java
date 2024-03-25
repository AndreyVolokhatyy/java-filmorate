package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
public class UserService {

    private UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("dataBaseUser") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addUser(User user) {
        return userStorage.heandlerUser(user);
    }

    public List<User> getListUsers() {
        return userStorage.getListUsers();
    }

    public User addFriends(int userId, int friendId) {
        userStorage.addFriend(userId, friendId);
        return userStorage.getUser(userId);
    }

    public User deleteFriends(int userId, int friendId) {
        userStorage.deleteFriend(userId, friendId);
        return userStorage.getUser(userId);
    }

    public Set<User> getFriends(int userId) {
        Set<User> users = new HashSet<>();
        userStorage.getUser(userId).getFriends()
                .forEach(f -> users.add(userStorage.getUser(f.getFollowedUserId())));
        return users;
    }

    public SortedSet<User> getCommonFriends(int userId, int anotherUserId) {
        TreeSet<User> tmp = new TreeSet<>(Comparator.comparing(User::getId));
        getFriends(userId)
                .stream()
                .filter(f -> getFriends(anotherUserId).contains(f))
                .forEach(tmp::add);
        return tmp;
    }

    public User getUser(int id) {
        return userStorage.getUser(id);
    }
}
