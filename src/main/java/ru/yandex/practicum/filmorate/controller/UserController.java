package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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

    private Map<Integer, User> users = DataControllers.getUsers();
    private int maxId;

    @GetMapping("/users")
    public List<User> listUsers() {
        return new ArrayList<>(users.values());
    }

    @RequestMapping(value = "/users",
            method = { RequestMethod.PUT, RequestMethod.POST })
    public User addUser(@RequestBody @Valid User user) {
        boolean skip = checkLogin(user.getLogin());
        if (!users.containsKey(user.getId()) && !skip) {
            User updateUser = fillEmptyName(user);
            if (maxId == 0) {
                maxId = 1;
                updateUser.setId(1);
            } else {
                maxId++;
                updateUser.setId(maxId);
            }
            users.put(updateUser.getId(), updateUser);
            return updateUser;
        } else if (users.containsKey(user.getId())) {
            User updateUser = fillEmptyName(user);
            updateUser.setId(user.getId());
            users.put(updateUser.getId(), updateUser);
            return updateUser;
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private User fillEmptyName(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return user;
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
}