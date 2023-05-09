package ru.yandex.practicum.filmorate.storage.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getAllMpa() {
        return jdbcTemplate.query("SELECT * FROM MPA_RATING", (rs, rowNum) -> new Mpa(
                rs.getInt("mpa_id"),
                rs.getString("mpa_name"))
        );
    }

    @Override
    public Mpa getMpaById(int id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT mpa_name FROM MPA_RATING WHERE mpa_id = ?", id);
        if (userRows.next()) {
            return new Mpa(id, userRows.getString("mpa_name"));
        } else {
            throw new FilmNotFoundException(String.format("Can't find rating: id=%d", id));
        }
    }

}