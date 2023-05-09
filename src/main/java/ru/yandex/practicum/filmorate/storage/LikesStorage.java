package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface LikesStorage {
    void add(int filmId, int userId);

    void delete(int filmId, int userId);

    List<Integer> findPopular(int count);
}
