package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film);

    Film getFilm(int id);

    HashMap<Integer, Film> getAllFilms();

    Film updateFilm(Film film);

    void addLike(Film film, User user);

    void removeLike(Film film, User user);

    List<Film> getTopFilms(int count);
}
