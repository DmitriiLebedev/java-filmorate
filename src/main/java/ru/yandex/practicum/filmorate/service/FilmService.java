package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.*;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FilmService {

    private final UserStorage userStorage;
    private final FilmStorage filmStorage;
    private final LikesStorage likesStorage;
    private final GenreStorage genreStorage;
    private final MpaStorage mpaStorage;

    public List<Film> getAllFilms() {
        return filmStorage.findAllFilms();
    }

    public Film getFilm(int id) {
        Film film = filmStorage.findFilmById(id);
        int mpaId = film.getMpa().getId();
        film.setMpa(mpaStorage.getMpaById(mpaId));
        film.setGenres(genreStorage.getFilmGenres(id));
        return film;
    }

    public Film addFilm(Film film) {
        filmStorage.save(film);
        genreStorage.saveGenresByFilm(film);
        return film;
    }

    public Film updateFilm(Film film) {
        filmStorage.update(film);
        genreStorage.deleteGenresByFilm(film);
        genreStorage.saveGenresByFilm(film);
        return getFilm(film.getId());
    }

    public void addLike(int filmId, int userId) {
        likesStorage.add(filmId, userId);
    }

    public void removeLike(int filmId, int userId) {
        if (userStorage.userExists(userId)) {
            throw new UserNotFoundException(String.format("Can't find user id=%d", userId));
        }
        likesStorage.delete(filmId, userId);
    }

    public List<Film> getTopFilms(int count) {
        return likesStorage.findPopular(count);
    }

}