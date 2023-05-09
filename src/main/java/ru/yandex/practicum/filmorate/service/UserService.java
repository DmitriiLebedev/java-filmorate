package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;

    public List<User> getAllUsers() {
        return userStorage.findAllUsers();
    }

    public User getUserByID(int id) {
        if (userStorage.userExists(id)) {
            throw new UserNotFoundException(String.format("Can't find user id=%d", id));
        }
        return userStorage.findUserById(id);
    }

    public List<User> getFriends(int id) {
        if (userStorage.userExists(id)) {
            throw new UserNotFoundException(String.format("Can't find user id=%d", id));
        }
        return friendsStorage.getFriends(id);
    }

    public User addUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        userStorage.save(user);
        return user;
    }

    public User updateUser(User user) {
        if (userStorage.userExists(user.getId())) {
            throw new UserNotFoundException(String.format("Can't find user id=%d", user.getId()));
        }
        userStorage.update(user);
        return user;
    }

    public void addFriend(int userId, int friendId) {
        if (userId == friendId) {
            throw new ValidationException("Id's of friends can't be equal");
        }
        if (userStorage.userExists(userId) || userStorage.userExists(friendId)) {
            throw new UserNotFoundException(String.format("Can't find user id=%d or friend id=%d",
                    userId, friendId));
        }
        friendsStorage.add(userId, friendId);
    }

    public void removeFriend(int userId, int friendId) {
        if (userId == friendId) {
            throw new ValidationException("You can't remove yourself from friends list");
        }
        if (userStorage.userExists(userId) || userStorage.userExists(friendId)) {
            throw new UserNotFoundException(String.format("Can't find user id=%d or friend id=%d",
                    userId, friendId));
        }
        friendsStorage.delete(userId, friendId);
    }

    public List<User> getCommonFriends(int id, int otherId) {
        List<User> list = getFriends(id);
        list.retainAll(getFriends(otherId));
        return list;
    }

}
