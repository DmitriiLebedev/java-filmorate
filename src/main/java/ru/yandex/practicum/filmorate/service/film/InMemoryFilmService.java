package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class InMemoryFilmService implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public InMemoryFilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    @Override
    public Film putFilm(Film film) {
        return filmStorage.putFilm(film);
    }

    @Override
    public Film getFilm(int id) {
        return filmStorage.getFilm(id);
    }

    @Override
    public ArrayList<Film> getAllFilms() {
        return new ArrayList<Film>(filmStorage.getAllFilms().values());
    }

    @Override
    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    @Override
    public void addLike(int filmId, int userId) {
        filmStorage.addLike(filmStorage.getFilm(filmId), userStorage.getUser(userId));
    }

    @Override
    public void removeLike(int filmId, int userId) {
        filmStorage.removeLike(filmStorage.getFilm(filmId), userStorage.getUser(userId));
    }

    @Override
    public List<Film> getTopFilms(int count) {
        return filmStorage.getTopFilms(count);
    }

}
