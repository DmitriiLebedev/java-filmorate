package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikesStorage {
    void add(int filmId, int userId);

    void delete(int filmId, int userId);

    List<Film> findPopular(int count);
}
