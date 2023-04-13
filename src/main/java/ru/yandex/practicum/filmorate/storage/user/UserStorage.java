package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface UserStorage {

    User addUser(User user);

    User getUser(int id);

    HashMap<Integer, User> getAllUsers();

    User updateUser(User user);

    void addFriend(User user, User user2);

    void removeFriend(User user, User user2);

    ArrayList<User> getFriends(User user);

    List<User> getCommonFriends(User user, User user2);
}
