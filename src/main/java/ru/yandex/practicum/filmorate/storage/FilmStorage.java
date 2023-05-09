package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film save(Film film);

    Film findFilmById(int id);

    Film update(Film film);

    List<Integer> findAllFilms();

    boolean filmExists(int id);
}
