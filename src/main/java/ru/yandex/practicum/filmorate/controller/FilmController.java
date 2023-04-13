package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public Film post(@Valid @RequestBody Film film) throws ValidationException {
        log.info("POST request for films");
        return filmService.putFilm(film);
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) throws ValidationException {
        log.info("PUT request for films");
        return filmService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void putLike(@PathVariable int id, @PathVariable int userId) {
        log.info("PUT like request for film id " + id + ", userId " + userId);
        filmService.addLike(id, userId);
    }

    @GetMapping
    public ArrayList<Film> getAll() {
        log.info("GET request for films");
        return filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    public Film get(@PathVariable int id) {
        log.info("GET request for specific film by id " + id);
        return filmService.getFilm(id);
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(required = false, defaultValue = "10") @Positive int count)
            throws ValidationException {
        log.info("GET request for top films with count parameter - " + count);
        return filmService.getTopFilms(count);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        log.info("DELETE like request for film id " + id + ", userId " + userId);
        filmService.removeLike(id, userId);
    }

}
