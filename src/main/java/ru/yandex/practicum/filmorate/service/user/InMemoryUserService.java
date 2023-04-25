package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class InMemoryUserService implements UserService {

    private final UserStorage userStorage;

    @Autowired
    public InMemoryUserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User addUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.addUser(user);
    }

    @Override
    public User getUser(int id) {
        if (userStorage.getUser(id) == null) {
            throw new UserNotFoundException("Can't find user with id " + id);
        } else {
            return userStorage.getUser(id);
        }
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return new ArrayList<User>(userStorage.getAllUsers().values());
    }

    @Override
    public User updateUser(User user) {
        if (userStorage.getUser(user.getId()) == null) {
            throw new UserNotFoundException("Can't update, there is no user with id " + user.getId());
        } else {
            if (user.getName() == null || user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            return userStorage.updateUser(user);
        }
    }

    @Override
    public void addFriend(int userId, int user2Id) {
        User user = userStorage.getUser(userId);
        User user2 = userStorage.getUser(user2Id);
        if (user == null) {
            throw new UserNotFoundException("Can't add friend, user id is incorrect " + userId);
        } else if (user2 == null) {
            throw new UserNotFoundException("Can't add friend, friend id is incorrect " + user2Id);
        } else {
            userStorage.addFriend(user, user2);
        }
    }

    @Override
    public void removeFriend(int userId, int user2Id) {
        User user = userStorage.getUser(userId);
        User user2 = userStorage.getUser(user2Id);
        if (user == null) {
            throw new UserNotFoundException("Can't remove friend, user id is incorrect " + userId);
        } else if (user2 == null) {
            throw new UserNotFoundException("Can't remove friend, friend id is incorrect " + user2Id);
        } else {
            userStorage.removeFriend(user, user2);
        }
    }

    @Override
    public ArrayList<User> getFriends(int id) {
        User user = userStorage.getUser(id);
        return userStorage.getFriends(user);
    }

    @Override
    public List<User> getCommonFriends(int userId, int user2Id) {
        User user = userStorage.getUser(userId);
        User user2 = userStorage.getUser(user2Id);
        if (user.getFriends() == null || user2.getFriends() == null) return Collections.emptyList();
        if (userStorage.getUser(userId) == null || userStorage.getUser(user2Id) == null) {
            throw new UserNotFoundException("Can't find user");
        }
        return userStorage.getCommonFriends(user, user2);
    }

}
