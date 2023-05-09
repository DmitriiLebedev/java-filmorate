package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreStorage {
    List<Genre> getAllGenres();

    Genre getGenreById(int id);

    void saveGenresByFilm(Film film);

    void deleteGenresByFilm(Film film);

    Set<Genre> getFilmGenres(int filmId);
}
