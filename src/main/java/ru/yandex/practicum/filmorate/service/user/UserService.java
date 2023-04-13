package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    User addUser(User user);

    User getUser(int id);

    ArrayList<User> getAllUsers();

    User updateUser(User user);

    void addFriend(int userId, int user2Id);

    void removeFriend(int userId, int user2Id);

    ArrayList<User> getFriends(int id);

    List<User> getCommonFriends(int userId, int user2Id);
}
