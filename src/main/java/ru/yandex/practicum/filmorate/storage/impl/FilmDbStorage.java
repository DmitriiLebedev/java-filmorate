package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MpaDbStorage mpaDbStorage;
    private final GenreDbStorage genreDbStorage;

    @Override
    public Film save(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO films (film_name, description, release_date, duration, mpa_id)" +
                            " VALUES (?, ?, ?, ?, ?)",
                    new String[]{"film_id"});
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getDuration());
            ps.setInt(5, film.getMpa().getId());
            return ps;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return findFilmById(film.getId());
    }

    @Override
    public Film findFilmById(int id) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT F.film_id, F.film_name, F.description, " +
                "F.release_date, F.duration, M.mpa_id, M.mpa_name " +
                "FROM films AS F " +
                "JOIN mpa_rating AS M ON F.mpa_id=M.mpa_id " +
                "WHERE F.film_id = ?", id);
        if (filmRows.next()) {
            return Film.builder()
                    .id(filmRows.getInt("film_id"))
                    .name(filmRows.getString("film_name"))
                    .description(filmRows.getString("description"))
                    .releaseDate(Objects.requireNonNull(filmRows.getDate("release_date")).toLocalDate())
                    .duration(filmRows.getInt("duration"))
                    .mpa(new Mpa(filmRows.getInt("mpa_id"), filmRows.getString("mpa_name")))
                    .build();
        } else {
            throw new FilmNotFoundException(String.format("Can't find film: id=%d in storage", id));
        }
    }

    @Override
    public Film update(Film film) {
        String sqlQuery = "UPDATE films SET film_name = ?," +
                " description = ?," +
                " release_date = ?," +
                " duration = ?," +
                " mpa_id = ? WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        return findFilmById(film.getId());
    }

    @Override
    public List<Film> findAllFilms() {
        List<Film> films = new ArrayList<>();
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT F.film_id, F.film_name, F.description, " +
                "F.release_date, F.duration, M.mpa_id, M.mpa_name " +
                "FROM films AS F " +
                "JOIN mpa_rating AS M ON F.mpa_id=M.mpa_id ");
        while (filmRows.next()) {
            Film film = Film.builder()
                    .id(filmRows.getInt("film_id"))
                    .name(filmRows.getString("film_name"))
                    .description(filmRows.getString("description"))
                    .releaseDate(Objects.requireNonNull(filmRows.getDate("release_date")).toLocalDate())
                    .duration(filmRows.getInt("duration"))
                    .mpa(new Mpa(filmRows.getInt("mpa_id"), filmRows.getString("mpa_name")))
                    .build();
            film.setGenres(genreDbStorage.getFilmGenres(film.getId()));
            films.add(film);
        }
        return films;
    }

    @Override
    public boolean filmExists(int id) {
        var sqlQuery = "SELECT film_id FROM films WHERE film_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, id);
        return rowSet.next();
    }

}