package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Validated
@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> listUsers() {
        return userService.getListUsers();
    }

    @RequestMapping(value = "/users",
            method = {RequestMethod.PUT, RequestMethod.POST})
    public User addUser(@RequestBody @Valid User user) {
        return userService.addUser(user);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public User addFriends(@PathVariable int id, @PathVariable int friendId) {
        return userService.addFriends(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable int id, @PathVariable int friendId) {
        userService.deleteFriends(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public Set<User> listFriends(@PathVariable int id) {
        return userService.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Set<User> listCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.getCommonFriends(id, otherId);
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUser(id);
    }
}