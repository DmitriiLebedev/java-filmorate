package ru.yandex.practicum.filmorate.storage.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class FriendsDbStorage implements FriendsStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getFriends(int id) {
        return jdbcTemplate.query("SELECT * FROM users WHERE user_id IN (SELECT friend_id AS id FROM friends" +
                " WHERE user_id = ?) ORDER BY user_id", (rs, rowNum) -> createUser(rs), id);
    }

    @Override
    public void add(int userId, int friendId) {
        jdbcTemplate.update("INSERT INTO friends(user_id, friend_id) VALUES(?,?)", userId, friendId);
    }

    @Override
    public void delete(int userId, int friendId) {
        jdbcTemplate.update("DELETE FROM friends WHERE user_id = ? AND friend_id = ?", userId, friendId);
    }

    private User createUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("user_id");
        String login = rs.getString("login");
        String name = rs.getString("user_name");
        String email = rs.getString("email");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        return new User(id, email, login, name, birthday);
    }

}