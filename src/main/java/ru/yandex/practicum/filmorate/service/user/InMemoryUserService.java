package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class InMemoryUserService implements UserService {

    private final UserStorage userStorage;

    @Autowired
    public InMemoryUserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User putUser(User user) {
        return userStorage.putUser(user);
    }

    @Override
    public User getUser(int id) {
        return userStorage.getUser(id);
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return new ArrayList<User>(userStorage.getAllUsers().values());
    }

    @Override
    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    @Override
    public void addFriend(int userId, int user2Id) {
        userStorage.addFriend(userStorage.getUser(userId), userStorage.getUser(user2Id));
    }

    @Override
    public void removeFriend(int userId, int user2Id) {
        userStorage.removeFriend(userStorage.getUser(userId), userStorage.getUser(user2Id));
    }

    @Override
    public ArrayList<User> getFriends(int id) {
        User user = userStorage.getUser(id);
        return userStorage.getFriends(user);
    }

    @Override
    public List<User> getCommonFriends(int userId, int user2Id) {
        if (userStorage.getUser(userId) == null || userStorage.getUser(user2Id) == null) {
            throw new UserNotFoundException("Can't find user");
        }
        return userStorage.getCommonFriends(userStorage.getUser(userId), userStorage.getUser(user2Id));
    }

}
