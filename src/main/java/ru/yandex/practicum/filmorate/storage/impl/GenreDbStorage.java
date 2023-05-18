package ru.yandex.practicum.filmorate.storage.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Repository
@AllArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAllGenres() {
        return jdbcTemplate.query("SELECT * FROM GENRES", ((rs, rowNum) -> new Genre(
                rs.getInt("genre_id"),
                rs.getString("genre_name"))
        ));
    }

    @Override
    public Genre getGenreById(int id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT genre_name FROM GENRES WHERE genre_id = ?", id);
        if (userRows.next()) {
            return new Genre(id, userRows.getString("genre_name"));
        } else {
            throw new FilmNotFoundException(String.format("Can't find genre: id=%d", id));
        }
    }

    @Override
    public Set<Genre> getFilmGenres(int filmId) {
        return new HashSet<>(jdbcTemplate.query("SELECT gen.genre_id, genre_name " +
                        "FROM GENRES AS gen " +
                        "JOIN FILMS_GENRES AS fil ON fil.genre_id = gen.genre_id " +
                        "                        WHERE film_id = ? " +
                        "                        ORDER BY gen.genre_id ", (rs, rowNum) -> new Genre(
                        rs.getInt("genre_id"),
                        rs.getString("genre_name")),
                filmId
        ));
    }

    @Override
    public void saveGenresByFilm(Film film) {
        if (film.getGenres() != null) {
            List<Genre> genreList = new ArrayList<>(film.getGenres());
            jdbcTemplate.batchUpdate("INSERT INTO FILMS_GENRES (film_id, genre_id) VALUES (?, ?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setLong(1, film.getId());
                            ps.setLong(2, genreList.get(i).getId());
                        }

                        @Override
                        public int getBatchSize() {
                            return film.getGenres().size();
                        }
                    });
        }
    }

    @Override
    public void deleteGenresByFilm(Film film) {
        jdbcTemplate.update("DELETE FROM FILMS_GENRES WHERE film_id = ?", film.getId());
    }

}
