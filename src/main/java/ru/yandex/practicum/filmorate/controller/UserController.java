package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 0;

    private int increaseId() {
        return ++id;
    }

    @PostMapping
    public User post(@Valid @RequestBody User user) {
        log.info("POST request for users");
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("username set to login in POST request for users");
        }
        user.setId(increaseId());
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
        log.info("PUT request for users");
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return user;
        } else {
            throw new ValidationException("Can't find user with " + user.getId() + " id");
        }
    }

    @GetMapping
    public List<User> get() {
        log.info("GET request for users");
        return new ArrayList<User>(users.values());
    }
}

