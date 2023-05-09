package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendsStorage {
    void add(int userId, int friendId);

    void delete(int userId, int friendId);

    List<User> getFriends(int id);
}
