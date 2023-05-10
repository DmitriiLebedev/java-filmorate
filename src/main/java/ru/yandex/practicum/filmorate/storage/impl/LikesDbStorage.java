package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.LikesStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LikesDbStorage implements LikesStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MpaDbStorage mpaDbStorage;
    private final GenreDbStorage genreDbStorage;

    @Override
    public void add(int filmId, int userId) {
        jdbcTemplate.update("INSERT INTO LIKES (film_id, user_id) VALUES (? , ?)", filmId, userId);
    }

    @Override
    public void delete(int filmId, int userId) {
        jdbcTemplate.update("DELETE FROM LIKES WHERE user_id = ? AND film_id = ?", userId, filmId);
    }

    @Override
    public List<Film> findPopular(int count) {
        String sqlQuery = "SELECT f.*, COUNT(l.film_id) AS count FROM films AS f " +
                "                LEFT JOIN likes l ON f.film_id=l.film_id " +
                "                GROUP BY f.film_id " +
                "                ORDER BY count DESC " +
                "                LIMIT ?";
        return jdbcTemplate.query(sqlQuery, this::buildFilm, count);
    }

    private Film buildFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = Film.builder()
                .id(resultSet.getInt("film_id"))
                .name(resultSet.getString("film_name"))
                .description(resultSet.getString("description"))
                .releaseDate(Objects.requireNonNull(resultSet.getDate("release_date")).toLocalDate())
                .duration(resultSet.getInt("duration"))
                .mpa(mpaDbStorage.getMpaById(resultSet.getInt("mpa_id")))
                .build();
        film.setGenres(genreDbStorage.getFilmGenres(film.getId()));
        return film;
    }

}
