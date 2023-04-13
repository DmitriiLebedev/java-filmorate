package ru.yandex.practicum.filmorate.storage.film;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private final Set<Film> filmsTreeSet = new TreeSet<>(Comparator.comparing(Film::getUsersSize).
            thenComparing(Film::getId).reversed());
    @Getter
    private int id = 0;

    private int increaseId() {
        return ++id;
    }

    @Override
    public Film putFilm(Film film) {
        film.setId(increaseId());
        films.put(film.getId(), film);
        filmsTreeSet.add(film);
        return film;
    }

    @Override
    public Film getFilm(int id) {
        if (!films.containsKey(id)) {
            throw new FilmNotFoundException("Can't get film with id " + id);
        }
        return films.get(id);
    }

    @Override
    public HashMap<Integer, Film> getAllFilms() {
        return films;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new FilmNotFoundException("Can't update, there is no film with id " + id);
        }
        filmsTreeSet.remove(films.get(film.getId()));
        filmsTreeSet.add(film);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void addLike(Film film, User user) {
        if (!films.containsKey(id)) {
            throw new FilmNotFoundException("Can't like film with id " + id);
        }
        films.get(film.getId()).getUsers().add(user.getId());
    }

    @Override
    public void removeLike(Film film, User user) {
        film.getUsers().remove(user.getId());
    }

    @Override
    public List<Film> getTopFilms(int count) {
        return filmsTreeSet.stream().limit(count).collect(Collectors.toList());
    }
}

