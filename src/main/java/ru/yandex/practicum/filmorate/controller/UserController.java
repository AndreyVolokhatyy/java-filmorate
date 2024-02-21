package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.data.DataControllers;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@Slf4j
public class UserController {

    private Map<String, User> users = DataControllers.getUsers();
    private int maxId;

    @GetMapping("/users")
    public List<User> listUsers() {
        return new ArrayList<>(users.values());
    }

    @PutMapping("/users")
    public User addUser(@RequestBody @Valid User user) {
        if (!users.containsKey(user.getLogin())) {
            User updateUser = fillEmptyName(user);
            if (maxId == 0) {
                maxId = 1;
                updateUser.setId(1);
            } else {
                maxId++;
                updateUser.setId(maxId);
            }
            users.put(updateUser.getLogin(), updateUser);
            return updateUser;
        } else {
            return null;
        }
    }

    @PostMapping("/users")
    public User updateUser(@RequestBody User user) {
        String login = user.getLogin();
        if (users.containsKey(login)) {
            User updateUser = fillEmptyName(user);
            updateUser.setId(users.get(login).getId());
            users.put(updateUser.getLogin(), updateUser);
            return updateUser;
        } else {
            log.info("User not found");
            return null;
        }
    }

    private User fillEmptyName(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return user;
    }
}