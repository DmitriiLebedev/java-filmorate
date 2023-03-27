package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final HashMap<Integer, Film> movies = new HashMap<>();
    private int id = 0;

    private int increaseId() {
        return ++id;
    }

    @PostMapping
    public Film post(@Valid @RequestBody Film film) {
        log.info("POST request for films");
        film.setId(increaseId());
        movies.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) {
        log.info("PUT request for films");
        if (movies.containsKey(film.getId())) {
            movies.put(film.getId(), film);
            return film;
        } else {
            throw new ValidationException("Can't find movie with " + film.getId() + " id");
        }
    }

    @GetMapping
    public List<Film> getAll() {
        log.info("GET request for films");
        return new ArrayList<Film>(movies.values());
    }
}
