package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

public interface FilmService {
    Film putFilm(Film film);

    Film getFilm(int id);

    ArrayList<Film> getAllFilms();

    Film updateFilm(Film film);

    void addLike(int filmId, int userId);

    void removeLike(int filmId, int userId);

    List<Film> getTopFilms(int count);
}
