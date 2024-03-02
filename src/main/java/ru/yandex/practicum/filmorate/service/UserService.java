package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.userStorage = inMemoryUserStorage;
    }

    public User addFriends(int userId, int friendId) {
        Map<Integer, User> users = userStorage.getUsers();
        User user = users.get(userId);
        User friend = users.get(friendId);
        user.getFriends().add(friend.getId());
        friend.getFriends().add(user.getId());
        return user;
    }

    public User deleteFriends(int userId, int friendId) {
        Map<Integer, User> users = userStorage.getUsers();
        User user = users.get(userId);
        User friend = users.get(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
        return user;
    }

    public Set<User> getFriends(int userId) {
        Map<Integer, User> users = userStorage.getUsers();
        TreeSet<User> tmp = new TreeSet<>(Comparator.comparing(User::getId));
        User user = users.get(userId);
        for (int id : user.getFriends()) {
            tmp.add(users.get(id));
        }
        return tmp;
    }

    public SortedSet<User> getCommonFriends(int userId, int anotherUserId) {
        Map<Integer, User> users = userStorage.getUsers();
        TreeSet<User> tmp = new TreeSet<>(Comparator.comparing(User::getId));
        User user = users.get(userId);
        List<Integer> tmpId = user.getFriends()
                .stream()
                .filter(f -> users.get(anotherUserId).getFriends().contains(f))
                .collect(Collectors.toList());
        for (int id : tmpId) {
            tmp.add(users.get(id));
        }
        return tmp;
    }
}
