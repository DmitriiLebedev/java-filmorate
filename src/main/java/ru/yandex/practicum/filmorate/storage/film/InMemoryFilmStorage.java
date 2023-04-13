package ru.yandex.practicum.filmorate.storage.film;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Integer, Film> films = new HashMap<>();
    @Getter
    private int id = 0;

    private int increaseId() {
        return ++id;
    }

    @Override
    public Film addFilm(Film film) {
        film.setId(increaseId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film getFilm(int id) {
        return films.get(id);
    }

    @Override
    public HashMap<Integer, Film> getAllFilms() {
        return films;
    }

    @Override
    public Film updateFilm(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void addLike(Film film, User user) {
        films.get(film.getId()).getUsers().add(user.getId());
    }

    @Override
    public void removeLike(Film film, User user) {
        film.getUsers().remove(user.getId());
    }

    @Override
    public List<Film> getTopFilms(int count) {
        return films.values()
                .stream()
                .sorted((f1, f2) -> f2.getUsersSize() - f1.getUsersSize())
                .limit(count)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

