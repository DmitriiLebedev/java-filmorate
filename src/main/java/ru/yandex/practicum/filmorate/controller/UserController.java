package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User post(@Valid @RequestBody User user) throws ValidationException {
        log.info("POST request for users");
        return userService.putUser(user);
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) throws ValidationException {
        log.info("PUT request for users");
        return userService.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void putFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("PUT request for user id " + id + " and friend id " + friendId);
        userService.addFriend(id, friendId);
    }

    @GetMapping
    public List<User> getAll() {
        log.info("GET request for users");
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        log.info("GET request for user id " + id);
        return userService.getUser(id);
    }

    @GetMapping("/{id}/friends")
    public ArrayList<User> getFriends(@PathVariable int id) {
        log.info("GET request for friends of user id " + id);
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommon(@PathVariable int id, @PathVariable int otherId) {
        log.info("GET request for common friends of user id " + id + " and " + otherId);
        return userService.getCommonFriends(id, otherId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("DELETE friend request for user id " + id + " and friend id " + friendId);
        userService.removeFriend(id, friendId);
    }
}

