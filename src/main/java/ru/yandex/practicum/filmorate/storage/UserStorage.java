package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User save(User user);

    User update(User user);

    List<User> findAllUsers();

    User findUserById(int id);

    void delete(User user);

    boolean userExists(int id);
}
