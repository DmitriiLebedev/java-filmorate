package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/mpa")
@AllArgsConstructor
public class MpaController {

    private final MpaService mpaService;

    @GetMapping("{id}")
    public Mpa getMpa(@PathVariable("id") int id) {
        log.info("GET request for mpa " + id);
        return mpaService.getMpa(id);
    }

    @GetMapping
    public List<Mpa> getAllMpa() {
        log.info("GET request for list of mpa");
        return mpaService.getAllMpa();
    }
}
