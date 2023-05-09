package ru.yandex.practicum.filmorate.storage.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.storage.LikesStorage;

import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class LikesDbStorage implements LikesStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void add(int filmId, int userId) {
        jdbcTemplate.update("INSERT INTO LIKES (film_id, user_id) VALUES (? , ?)", filmId, userId);
    }

    @Override
    public void delete(int filmId, int userId) {
        jdbcTemplate.update("DELETE FROM LIKES WHERE user_id = ? AND film_id = ?", userId, filmId);
    }

    @Override
    public List<Integer> findPopular(int count) {
        String sql = "SELECT film_id FROM LIKES GROUP BY film_id ORDER BY COUNT(user_id) DESC LIMIT ?";
        return jdbcTemplate.queryForList(sql, int.class, count);
    }

}
