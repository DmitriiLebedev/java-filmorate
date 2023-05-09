package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/genres")
@AllArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("{id}")
    public Genre getGenre(@PathVariable("id") int id) {
        log.info("GET request for genre " + id);
        return genreService.getGenre(id);
    }

    @GetMapping
    public List<Genre> getAllGenres() {
        log.info("GET request for genres");
        return genreService.getAllGenres();
    }
}
