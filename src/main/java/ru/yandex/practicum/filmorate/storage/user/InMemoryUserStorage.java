package ru.yandex.practicum.filmorate.storage.user;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class InMemoryUserStorage implements UserStorage {

    private Map<Integer, User> users;

    private int maxId;

    public InMemoryUserStorage() {
        users = new HashMap<>();
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user.getId());
    }

    @Override
    public User updater(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public ArrayList<User> getListUsers() {
        return new ArrayList<>(users.values());
    }

    public User getUser(int id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public User heandlerUser(User user) {
        boolean skip = checkLogin(user.getLogin());
        User updateUser = fillEmptyName(user);
        if (!users.containsKey(user.getId()) && !skip) {
            maxId++;
            updateUser.setId(maxId);
            return updater(user);
        } else if (users.containsKey(user.getId())) {
            return updater(user);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean checkLogin(String str) {
        boolean skip = false;
        for (User u : users.values()) {
            if (u.getLogin().equals(str)) {
                skip = true;
            }
        }
        return skip;
    }

    private User fillEmptyName(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return user;
    }
}
